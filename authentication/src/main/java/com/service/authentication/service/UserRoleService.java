package com.service.authentication.service;

import com.service.authentication.dto.UserRoleDto;
import java.util.List;
import java.util.UUID;

public interface UserRoleService {
  UserRoleDto create(UserRoleDto dto);

  UserRoleDto getById(UUID id);

  List<UserRoleDto> getAll();

  UserRoleDto update(UUID id, UserRoleDto dto);

  void delete(UUID id);
}
