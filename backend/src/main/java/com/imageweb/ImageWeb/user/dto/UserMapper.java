package com.imageweb.ImageWeb.user.dto;

import com.imageweb.ImageWeb.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);
}
