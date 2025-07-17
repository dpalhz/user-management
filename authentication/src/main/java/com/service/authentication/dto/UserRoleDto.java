package com.service.authentication.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class UserRoleDto {
    private UUID id;
    private String name;
    
}
