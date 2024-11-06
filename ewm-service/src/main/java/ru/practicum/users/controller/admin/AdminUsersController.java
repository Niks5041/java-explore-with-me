package ru.practicum.users.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.users.dto.UserCreateDto;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
public class AdminUsersController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto add(@RequestBody @Valid UserCreateDto userCreateDto) {
        log.info("==> POST. Создание пользователя: {}", userCreateDto);
        UserDto receivedUserDto = userService.add(userCreateDto);
        log.info("<== POST. Создан пользовател: {}", receivedUserDto);
        return receivedUserDto;
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("userId") int userId) {
        log.info("==> DELETE. Удаление пользователя: {}", userId);
        userService.delete(userId);
        log.info("<== DELETE. Пользователь удален: {}", userId);
    }

    @GetMapping
    public List<UserDto> getAll(
            @RequestParam(required = false) Long[] ids,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("==> GET. Получение пользователей по id: {}, from: {}, size {}", ids, from, size);
        AdminUsersGetAllParams adminUsersGetAllParams = new AdminUsersGetAllParams(ids, from, size);
        List<UserDto> receivedUserDtoList = userService.getAll(adminUsersGetAllParams);
        log.info("<== GET. Получен список пользователей: " +  receivedUserDtoList);
        return receivedUserDtoList;
    }
}
