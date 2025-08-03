package com.service.authentication.dto;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserDto {
    @Schema(description = "Unique UUID identifier of the user",
    example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Username of the user", example = "user")
    private String username;

    @Schema(description = "Email address of the user", example = "user@example")
    private String email;

    @Schema(description = "Indicates if the user account is enabled", example = "true") 
    private boolean enabled;

    @Schema(description = "Set of roles assigned to the user")
    private List<UserRoleDto> roles;

}
