package com.service.authentication.dto.response;

import com.service.authentication.dto.TokenDto;
import com.service.authentication.dto.UserProfileDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
  @Schema(description = "Token response containing access and refresh tokens")
  private TokenDto tokenDto;

  @Schema(description = "User details of the logged-in user")
  private UserProfileDto userProfile;
}
