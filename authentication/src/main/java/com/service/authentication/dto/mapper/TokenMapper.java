package com.service.authentication.dto.mapper;

import org.mapstruct.Mapper;

import com.service.authentication.dto.TokenDto;


@Mapper(componentModel = "spring")
public interface TokenMapper {

  default TokenDto toTokenDto(String accessToken, String refreshToken) {
    return new TokenDto(accessToken, refreshToken);
  }
}
