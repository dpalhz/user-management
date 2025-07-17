package com.service.authentication.service;
import com.service.authentication.config.JwtConfig;
import com.service.authentication.dto.response.LoginResponse;
import com.service.authentication.dto.response.TokenResponse;
import com.service.authentication.dto.request.LoginRequest;
import com.service.authentication.dto.request.RegisterRequest;
import com.service.authentication.dto.request.TokenRefreshRequest;
import com.service.authentication.security.JwtTokenProvider;
import com.service.authentication.kafka.EmailKafkaProducer;
import com.service.authentication.entity.User;
import com.service.authentication.entity.UserRole;
import com.service.authentication.entity.UserRoleAssignment;
import com.service.authentication.repository.RefreshTokenRepository;
import com.service.authentication.repository.UserRepository;
import com.service.authentication.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RefreshTokenRepository refreshTokenRepo;
    private final JwtTokenProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtConfig jwtConfig;
    private final EmailKafkaProducer emailProducer;


    @Override
    public void register(RegisterRequest request) {
        UserRole defaultRole = userRoleRepository.findByName("REGULAR")
            .orElseThrow(() -> new RuntimeException("Default role REGULAR not found"));

        User user = User.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .enabled(true)
            .build();

        user = userRepository.save(user);

        UserRoleAssignment roleAssignment = UserRoleAssignment.builder()
            .user(user)
            .userRole(defaultRole)
            .grantedBy("SYSTEM") 
            .build();

        user.getRoleAssignments().add(roleAssignment);
        userRepository.save(user);

        String verificationToken = UUID.randomUUID().toString();
        emailProducer.sendEmailVerification(user.getEmail(), verificationToken, user.getId());
    }


    @Override
    public LoginResponse login(LoginRequest request) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String roleName = user.getRoleAssignments().stream()
                .map(assign -> assign.getUserRole().getName())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User has no roles"));

        String access = jwtProvider.generateToken(
                user.getId().toString(),
                roleName,
                jwtConfig.getAccessTokenExpirationMs()
        );

        String refresh = UUID.randomUUID().toString();
        refreshTokenRepo.save(user.getId(), refresh, jwtConfig.getRefreshTokenExpirationMs());

        return new LoginResponse(access, refresh);
    }


    @Override
    public TokenResponse refreshToken(TokenRefreshRequest request) {
        UUID userId = refreshTokenRepo.findUserIdByToken(request.getRefreshToken());
        if (userId == null) {
            throw new RuntimeException("Invalid or expired refresh token");
        }
    
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    
        String roleName = user.getRoleAssignments().stream()
                .map(assign -> assign.getUserRole().getName())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User has no roles"));
    
        String newAccess = jwtProvider.generateToken(
                user.getId().toString(),
                roleName,
                jwtConfig.getAccessTokenExpirationMs()
        );
    
        return new TokenResponse(newAccess);
    }
    

    @Override
    public void logout(String authHeader) {
        String userId = jwtProvider.getClaims(authHeader.substring(7)).getSubject();
        refreshTokenRepo.deleteByUserId(UUID.fromString(userId));
    }
}
