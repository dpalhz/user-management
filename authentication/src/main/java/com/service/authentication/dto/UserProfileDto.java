package com.service.authentication.dto;

import java.time.LocalDate;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserProfileDto {

  @Schema(description = "User details including profile information")
  private UserDto user;

  @Schema(description = "Unique UUID identifier of the user profile",
          example = "123e4567-e89b-12d3-a456-426614174000") 
  private UUID id;

  @Schema(description = "Full name of the user", example = "John Doe")
  private String name;

  @Schema(description = "URL of the user's avatar image", example = "https://example.com/avatar.jpg")
  private String avatarUrl;

  @Schema(description = "User's birth date in ISO format", example = "1990-01-01")  
  private LocalDate birthDate;

  @Schema(description = "A short biography of the user", example = "Software developer with a passion for open source.")
  private String bio;
}
