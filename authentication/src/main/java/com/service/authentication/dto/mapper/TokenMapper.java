package com.service.authentication.dto.mapper;

import com.service.authentication.dto.response.TokenResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenMapper {

  default TokenResponse toTokenResponse(String accessToken, String refreshToken) {
    return new TokenResponse(accessToken, refreshToken);
  }
}
