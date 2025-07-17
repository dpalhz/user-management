package com.service.authentication.service;
import com.service.authentication.dto.response.LoginResponse;
import com.service.authentication.dto.response.TokenResponse;
import com.service.authentication.dto.request.LoginRequest;
import com.service.authentication.dto.request.RegisterRequest;
import com.service.authentication.dto.request.TokenRefreshRequest;
public interface AuthService {
    void register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    TokenResponse refreshToken(TokenRefreshRequest request);
    void logout(String accessTokenHeader);
}
