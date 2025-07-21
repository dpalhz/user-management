package com.service.authentication.dto.mapper;

import com.service.authentication.dto.response.LoginResponse;
import com.service.authentication.dto.response.TokenResponse;
import com.service.authentication.dto.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {

  default LoginResponse toLoginResponse(
      String accessToken, String refreshToken, UserResponse userResponse) {
    TokenResponse tokenResponse = new TokenResponse(accessToken, refreshToken);
    return LoginResponse.builder().tokenResponse(tokenResponse).userResponse(userResponse).build();
  }
}
