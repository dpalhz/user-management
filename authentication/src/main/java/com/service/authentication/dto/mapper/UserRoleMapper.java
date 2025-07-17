package com.service.authentication.dto.mapper;
import com.service.authentication.dto.UserRoleDto;
import com.service.authentication.entity.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
    UserRoleDto toDto(UserRole entity);
    @Mapping(target = "assignments", ignore = true)
    UserRole toEntity(UserRoleDto dto);
}