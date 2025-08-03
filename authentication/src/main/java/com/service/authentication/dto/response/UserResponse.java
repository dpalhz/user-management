package com.service.authentication.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
  @Schema(
      description = "Unique UUID identifier of the user",
      example = "123e4567-e89b-12d3-a456-426614174000")
  private String id;

  @Schema(description = "Email address of the user", example = "user@example")
  private String email;

  @Schema(description= "Username of the user", example = "user" )
  private String username;

  
  @Schema(description = "Indicates if the user account is enabled", example = "true")
  private boolean enabled;
}
