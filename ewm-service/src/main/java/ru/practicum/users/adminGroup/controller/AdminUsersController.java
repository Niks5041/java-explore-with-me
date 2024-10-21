package ru.practicum.users.adminGroup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.users.dto.NewUserRequestDto;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.service.UserService;

import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
public class AdminUsersController {

    private final UserService usersService;

    @GetMapping
    public Collection<UserDto> getUsers(@RequestParam List<Integer> ids,
                                                @RequestParam(defaultValue = "0") Integer from,
                                                @RequestParam(defaultValue = "10") Integer size) {
        log.info("Пришел GET/admin/users запрос с параметрами ids = {}, from = {}, size = {}", ids, from, size);
        Collection<UserDto> users = usersService.getUsers(ids, from, size);
        log.info("Отправлен GET/admin/users ответ: " + users);
        return users;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto saveUser(@RequestBody NewUserRequestDto newUserRequestDto) {
        log.info("Пришел GET/admin/users запрос с телом: " + newUserRequestDto);
        UserDto userDto = usersService.saveUser(newUserRequestDto);
        log.info("Отправлен GET/admin/users ответ c телом: " + userDto);
        return userDto;
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        log.info("Пришел DELETE/admin/users запрос с ID: {}", userId);
        usersService.deleteUser(userId);
        log.info("Отправлен ответ DELETE/admin/users запрос с ID: {}", userId);
    }
}
