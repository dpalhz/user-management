package com.service.authentication.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

  @NotBlank(message = "Email must not be empty")
  @Email(message = "Invalid email format")
  @Schema(example = "user@example.com")
  private String email;

  @NotBlank(message = "Name is required")
  @Schema(example = "John Doe")
  private String name;

  @NotBlank(message = "Password is required")
  @Size(min = 8, message = "Password must be at least 8 characters long")
  @Schema(example = "mySecurePassword123")
  private String password;
}
