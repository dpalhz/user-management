package com.service.authentication.service.implementation;

import com.service.authentication.config.JwtConfig;
import com.service.authentication.dto.mapper.LoginMapper;
import com.service.authentication.dto.mapper.TokenMapper;
import com.service.authentication.dto.mapper.UserMapper;
import com.service.authentication.dto.request.LoginRequest;
import com.service.authentication.dto.request.RegisterRequest;
import com.service.authentication.dto.request.TokenRefreshRequest;
import com.service.authentication.dto.response.LoginResponse;
import com.service.authentication.dto.response.TokenResponse;
import com.service.authentication.dto.response.UserResponse;
import com.service.authentication.entity.User;
import com.service.authentication.entity.UserRole;
import com.service.authentication.entity.UserRoleAssignment;
import com.service.authentication.exception.AuthErrorCode;
import com.service.authentication.exception.AuthException;
// import com.service.authentication.kafka.EmailKafkaProducer;
import com.service.authentication.repository.UserRepository;
import com.service.authentication.repository.UserRoleRepository;
import com.service.authentication.security.JwtTokenProvider;
import com.service.authentication.service.AuthService;
import com.service.authentication.service.RefreshTokenRedisService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final UserRoleRepository userRoleRepository;
  private final RefreshTokenRedisService refreshTokenRedisService;
  private final JwtTokenProvider jwtProvider;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authManager;
  private final JwtConfig jwtConfig;
  private final LoginMapper loginMapper;
  private final TokenMapper tokenMapper;
  private final UserMapper userMapper;

  // private final EmailKafkaProducer emailProducer;s

  @Override
  public void register(RegisterRequest request) {
    // Check if email already registered
    userRepository
        .findByEmail(request.getEmail())
        .ifPresent(
            user -> {
              throw new AuthException(AuthErrorCode.AUTH_DUPLICATE_EMAIL);
            });

    // Assign default role
    // Assuming "REGULAR" is the default role
    UserRole defaultRole =
        userRoleRepository
            .findByName("REGULAR")
            .orElseThrow(
                () ->
                    new AuthException(
                        AuthErrorCode.AUTH_PROVIDER_MISMATCH)); // atau buat error khusus

    User user =
        User.builder()
            .username(request.getEmail().split("@")[0])
            .email(request.getEmail())
            .name(request.getName())
            .password(passwordEncoder.encode(request.getPassword()))
            .enabled(true)
            .build();

    user = userRepository.save(user);

    UserRoleAssignment roleAssignment =
        UserRoleAssignment.builder().user(user).userRole(defaultRole).build();

    user.getRoleAssignments().add(roleAssignment);
    userRepository.save(user);

    // Optionally, send verification email
    // String verificationToken = UUID.randomUUID().toString();
    // emailProducer.sendEmailVerification(user.getEmail(), verificationToken, user.getId());
  }

  @Override
  public LoginResponse login(LoginRequest request) {

    User user =
        userRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new AuthException(AuthErrorCode.AUTH_USER_NOT_FOUND));
    if (!user.isEnabled()) {
      throw new AuthException(AuthErrorCode.AUTH_USER_NOT_ENABLED);
    }

    try {
      authManager.authenticate(
          new UsernamePasswordAuthenticationToken(user.getUsername(), request.getPassword()));
    } catch (Exception ex) {
      throw new AuthException(AuthErrorCode.AUTH_INVALID_CREDENTIALS);
    }

    String roleName =
        user.getRoleAssignments().stream()
            .map(assign -> assign.getUserRole().getName())
            .findFirst()
            .orElseThrow(() -> new AuthException(AuthErrorCode.AUTH_ACCESS_DENIED));

    String access =
        jwtProvider.generateToken(
            user.getUsername(), roleName, jwtConfig.getAccessTokenExpirationMs());

    String refresh = UUID.randomUUID().toString();
    refreshTokenRedisService.saveRefreshToken(
        user.getId().toString(), refresh, jwtConfig.getRefreshTokenExpirationMs());

    UserResponse userResponse = userMapper.toUserResponse(user);
    LoginResponse loginResponse = loginMapper.toLoginResponse(access, refresh, userResponse);

    return loginResponse;
  }

  @Override
  public TokenResponse refreshToken(TokenRefreshRequest request) {
    UUID userId = refreshTokenRedisService.getUserIdFromRefreshToken(request.getRefreshToken());

    if (userId == null) {
      throw new AuthException(AuthErrorCode.AUTH_REFRESH_TOKEN_INVALID);
    }

    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new AuthException(AuthErrorCode.AUTH_USER_NOT_FOUND));

    String roleName =
        user.getRoleAssignments().stream()
            .map(assign -> assign.getUserRole().getName())
            .findFirst()
            .orElseThrow(() -> new AuthException(AuthErrorCode.AUTH_ACCESS_DENIED));

    String newAccess =
        jwtProvider.generateToken(
            user.getId().toString(), roleName, jwtConfig.getAccessTokenExpirationMs());

    // String newRefresh = jwtProvider.generateToken(
    //         user.getId().toString(),
    //         roleName,
    //         jwtConfig.getRefreshTokenExpirationMs()
    // );

    // refreshTokenRedisService.saveRefreshToken(
    //         user.getId().toString(),
    //         newRefresh,
    //         jwtConfig.getRefreshTokenExpirationMs()
    // );

    // return new TokenResponse(newAccess, newRefresh);
    return tokenMapper.toTokenResponse(newAccess, request.getRefreshToken());
  }

  @Override
  public void logout(String refreshToken) {
    if (refreshToken == null || refreshToken.isEmpty()) {
      throw new AuthException(AuthErrorCode.AUTH_TOKEN_MISSING);
    }

    boolean deleted = refreshTokenRedisService.deleteRefreshToken(refreshToken);

    if (Boolean.FALSE.equals(deleted)) {
      throw new AuthException(AuthErrorCode.AUTH_TOKEN_INVALID);
    }
  }
}
