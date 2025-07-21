package com.service.authentication.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class UserRoleDto {
  private UUID id;
  private String name;
}
