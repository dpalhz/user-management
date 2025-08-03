package com.service.authentication.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.service.authentication.dto.UserProfileDto;
import com.service.authentication.dto.request.RegisterRequest;
import com.service.authentication.entity.User;
import com.service.authentication.entity.UserProfile;

@Mapper(componentModel = "spring",uses = { UserMapper.class })
public interface UserProfileMapper {


    @Mapping(target = "user", source = "user")
    UserProfileDto toUserProfileDto(UserProfile entity);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "avatarUrl", ignore = true)
    @Mapping(target = "birthDate", ignore = true)
    @Mapping(target = "bio", ignore = true)
    UserProfile toUserProfile(RegisterRequest request, User user);


    @Mapping(target = "id", source = "dto.id") 
    @Mapping(target = "user", source = "user")
    UserProfile toUserProfile(UserProfileDto dto, User user);


}