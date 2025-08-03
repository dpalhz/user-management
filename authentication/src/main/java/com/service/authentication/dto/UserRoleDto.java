package com.service.authentication.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder 
public class UserRoleDto {
  private UUID id;
  private String name;
}
