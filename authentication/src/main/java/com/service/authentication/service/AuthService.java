package com.service.authentication.service;

import com.service.authentication.dto.TokenDto;
import com.service.authentication.dto.request.LoginRequest;
import com.service.authentication.dto.request.RegisterRequest;
import com.service.authentication.dto.request.TokenRefreshRequest;
import com.service.authentication.dto.response.LoginResponse;

public interface AuthService {
  void register(RegisterRequest request);

  LoginResponse login(LoginRequest request);

  TokenDto refreshToken(TokenRefreshRequest request);

  void logout(String refreshToken);
}
