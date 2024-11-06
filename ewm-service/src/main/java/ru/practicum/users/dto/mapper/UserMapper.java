package ru.practicum.users.dto.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.users.dto.UserCreateDto;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.dto.UserShortDto;
import ru.practicum.users.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User userCreateDtoToUser(UserCreateDto userCreateDto);

    UserDto userToUserDto(User user);

    UserShortDto userToUserShotDto(User user);
}
