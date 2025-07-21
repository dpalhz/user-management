package com.service.authentication.dto.mapper;

import com.service.authentication.dto.response.UserResponse;
import com.service.authentication.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "id", expression = "java(user.getId().toString())")
  @Mapping(target = "role", source = "user", qualifiedByName = "extractRoleName")
  UserResponse toUserResponse(User user);

  @Named("extractRoleName")
  static String extractRoleName(User user) {
    return user.getRoleAssignments().stream()
        .map(ra -> ra.getUserRole().getName())
        .findFirst()
        .orElse(null);
  }
}
