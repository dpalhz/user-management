package com.service.authentication.service.implementation;

import com.service.authentication.config.JwtConfig;
import com.service.authentication.dto.TokenDto;
import com.service.authentication.dto.mapper.AuthMapper;
import com.service.authentication.dto.mapper.TokenMapper;
import com.service.authentication.dto.mapper.UserMapper;
import com.service.authentication.dto.mapper.UserProfileMapper;
import com.service.authentication.dto.request.LoginRequest;
import com.service.authentication.dto.request.RegisterRequest;
import com.service.authentication.dto.request.TokenRefreshRequest;
import com.service.authentication.dto.response.LoginResponse;
import com.service.authentication.entity.UserProfile;
import com.service.authentication.entity.User;
import com.service.authentication.entity.UserRole;
import com.service.authentication.entity.UserRoleAssignment;
import com.service.authentication.exception.AuthErrorCode;
import com.service.authentication.exception.AuthException;
import com.service.authentication.repository.ProfileRepository;
// import com.service.authentication.kafka.EmailKafkaProducer;
import com.service.authentication.repository.UserRepository;
import com.service.authentication.repository.UserRoleAssignmentRepository;
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
  private final ProfileRepository profileRepository;
  private final UserRoleAssignmentRepository userRoleAssignmentRepository;
  private final RefreshTokenRedisService refreshTokenRedisService;
  private final JwtTokenProvider jwtProvider;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authManager;
  private final JwtConfig jwtConfig;
  private final TokenMapper tokenMapper;
  private final AuthMapper authMapper;
  private final UserMapper userMapper;
  private final UserProfileMapper userProfileMapper;

  private static final String DEFAULT_ROLE = "REGULAR";

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

    UserRole defaultRole =
        userRoleRepository
            .findByName(DEFAULT_ROLE)
            .orElseThrow(
                () ->
                    new AuthException(
                        AuthErrorCode.AUTH_PROVIDER_MISMATCH));

    User user = userMapper.toUser(request);
    user.setPassword(passwordEncoder.encode(request.getPassword()));


    UserRoleAssignment roleAssignment =
        UserRoleAssignment.builder().user(user)
                                    .userRole(defaultRole)
                                    .build();

    user.getRoleAssignments().add(roleAssignment);
    
    UserProfile profile = userProfileMapper.toUserProfile(request, user);  


    userRepository.save(user);
    userRoleAssignmentRepository.save(roleAssignment);
    profileRepository.save(profile);

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

    UserProfile profile = profileRepository.findByUser(user)
    .orElseThrow(() -> new AuthException(AuthErrorCode.AUTH_PROFILE_NOT_FOUND));

    TokenDto tokenDto = tokenMapper.toTokenDto(access, refresh);
    LoginResponse loginResponse = authMapper.toLoginResponse(tokenDto, userProfileMapper.toUserProfileDto(profile));

    return loginResponse;
  }

  @Override
  public TokenDto refreshToken(TokenRefreshRequest request) {
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
    return tokenMapper.toTokenDto(newAccess, request.getRefreshToken());
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
