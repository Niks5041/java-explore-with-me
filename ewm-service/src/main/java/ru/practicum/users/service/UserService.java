package ru.practicum.users.service;


import ru.practicum.users.dto.NewUserRequestDto;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.dto.requests.RequestDto;

import java.util.Collection;
import java.util.List;

public interface UserService {

    void deleteUser(Integer userId);

    Collection<UserDto> getUsers(List<Integer> ids, Integer from, Integer size);

    UserDto saveUser(NewUserRequestDto newUserRequestDto);

    Collection<RequestDto> getUserRequests(Integer userId);

    RequestDto saveUserRequest(Integer userId, Integer eventId);

   RequestDto cancelUserRequest(Integer userId, Integer requestId);
}


