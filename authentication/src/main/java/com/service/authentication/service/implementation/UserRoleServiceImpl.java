package com.service.authentication.service.implementation;

import com.service.authentication.dto.UserRoleDto;
import com.service.authentication.repository.UserRoleRepository;
import com.service.authentication.service.UserRoleService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

  private final UserRoleRepository UserRoleRepo;

  @Override
  public UserRoleDto create(UserRoleDto dto) {
    return null; // Implement update logic if needed, or throw an exception
  }

  @Override
  public UserRoleDto getById(UUID id) {
    return null; // Implement update logic if needed, or throw an exception
  }

  @Override
  public List<UserRoleDto> getAll() {
    return null; // Implement update logic if needed, or throw an exception
  }

  @Override
  public UserRoleDto update(UUID id, UserRoleDto dto) {
    return null; // Implement update logic if needed, or throw an exception
  }

  @Override
  public void delete(UUID id) {
    UserRoleRepo.deleteById(id);
  }
}
