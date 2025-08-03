package com.service.authentication.dto.mapper;

import com.service.authentication.dto.UserDto;
import com.service.authentication.dto.UserRoleDto;
import com.service.authentication.dto.request.RegisterRequest;
import com.service.authentication.entity.User;
import com.service.authentication.entity.UserRoleAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "username", expression = "java(com.service.authentication.dto.mapper.UserMapper.extractUsername(request.getEmail()))")
  @Mapping(target = "roleAssignments", ignore = true)
  @Mapping(target = "enabled", constant = "true")
  @Mapping(target = "password", ignore = true) // will be set manually
  User toUser(RegisterRequest request);


  @Mapping(target = "roles", source = "user", qualifiedByName = "mapRoles")
  UserDto toUserDto(User user);

  static String extractUsername(String email) {
    if (email == null) return null;
    return email.split("@")[0];
  }

  @Named("mapRoles")
  static List<UserRoleDto> mapRoles(User user) {
    if (user.getRoleAssignments() == null) return Collections.emptyList();
    return user.getRoleAssignments().stream()
      .filter(assignment -> assignment.getUserRole() != null)
      .map(UserRoleAssignment::getUserRole)
      .map(role -> UserRoleDto.builder()
        .id(role.getId())
        .name(role.getName())
        .build())
      .collect(Collectors.toList());
  }

}
