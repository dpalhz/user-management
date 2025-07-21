package com.service.authentication.dto.mapper;

import com.service.authentication.dto.UserRoleDto;
import com.service.authentication.entity.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
  @Mapping(target = "id", source = "userRole.id")
  @Mapping(target = "name", source = "userRole.name")
  UserRoleDto toUserRoleDto(UserRole userRole);

  @Mapping(target = "id", source = "dto.id")
  @Mapping(target = "name", source = "dto.name")
  @Mapping(target = "assignments", ignore = true)
  UserRole toUserRole(UserRoleDto dto);
}
