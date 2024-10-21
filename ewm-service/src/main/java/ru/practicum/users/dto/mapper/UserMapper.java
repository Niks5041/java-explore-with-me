package ru.practicum.users.dto.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.users.dto.NewUserRequestDto;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.dto.requests.RequestDto;
import ru.practicum.users.model.Request;
import ru.practicum.users.model.User;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(NewUserRequestDto newUserRequestDto);

    UserDto toUserDto(User user);

    RequestDto toRequestDto(Request userRequestDto);
}
