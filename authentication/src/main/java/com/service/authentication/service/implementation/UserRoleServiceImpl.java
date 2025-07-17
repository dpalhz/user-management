package com.service.authentication.service.implementation;
import com.service.authentication.dto.UserRoleDto;
import com.service.authentication.entity.UserRole;
import com.service.authentication.repository.UserRoleRepository;
import com.service.authentication.service.UserRoleService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.service.authentication.dto.mapper.UserRoleMapper;


import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository UserRoleRepo;
    private final UserRoleMapper UserRoleMapper;

    @Override
    public UserRoleDto create(UserRoleDto dto) {
        UserRole saved = UserRoleRepo.save(UserRoleMapper.toEntity(dto));
        return UserRoleMapper.toDto(saved);
    }

    @Override
    public UserRoleDto getById(UUID id) {
        return UserRoleRepo.findById(id)
                .map(UserRoleMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    @Override
    public List<UserRoleDto> getAll() {
        return UserRoleRepo.findAll().stream()
                .map(UserRoleMapper::toDto)
                .toList();
    }

    @Override
    public UserRoleDto update(UUID id, UserRoleDto dto) {
        UserRole role = UserRoleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        role.setName(dto.getName());
        return UserRoleMapper.toDto(UserRoleRepo.save(role));
    }

    @Override
    public void delete(UUID id) {
        UserRoleRepo.deleteById(id);
    }
}
