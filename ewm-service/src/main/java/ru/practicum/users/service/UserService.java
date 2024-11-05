package ru.practicum.users.service;


import ru.practicum.users.controller.admin.AdminUsersGetAllParams;
import ru.practicum.users.dto.UserCreateDto;
import ru.practicum.users.dto.UserDto;
import java.util.List;

public interface UserService {

    void delete(long userId);

    List<UserDto> getAll(AdminUsersGetAllParams adminUsersGetAllParams);

    UserDto add(UserCreateDto userCreateDto);

}


