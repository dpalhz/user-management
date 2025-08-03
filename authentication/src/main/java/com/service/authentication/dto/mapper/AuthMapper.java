package com.service.authentication.dto.mapper;

import com.service.authentication.dto.TokenDto;
import com.service.authentication.dto.UserProfileDto;
import com.service.authentication.dto.response.LoginResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    @Mapping(target = "tokenDto", source = "tokenDto")  
    @Mapping(target = "userProfile", source = "userResponse")
    LoginResponse toLoginResponse(TokenDto tokenDto, UserProfileDto userResponse);
}
