package com.service.authentication.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LogoutRequest {
  @Schema(description = "The refresh token to be invalidated", required = true)
  @NotBlank(message = "Refresh token must not be blank")
  private String refreshToken;
}
