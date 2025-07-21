package com.service.authentication.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
  @Schema(description = "Token response containing access and refresh tokens")
  private TokenResponse tokenResponse;
  
  @Schema(description = "User response containing user details")
  private UserResponse userResponse;
}
