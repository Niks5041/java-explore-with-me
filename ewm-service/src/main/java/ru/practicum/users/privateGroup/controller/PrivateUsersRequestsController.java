package ru.practicum.users.privateGroup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exceptions.ValidationException;
import ru.practicum.users.dto.requests.RequestDto;
import ru.practicum.users.service.UserService;

import java.util.Collection;


@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
public class PrivateUsersRequestsController {

    private final UserService usersService;

    @GetMapping
    public Collection<RequestDto> getUserRequests(@PathVariable Integer userId) {
        log.info("Пришел GET/users/{}/requests запрос с параметром userId = {}", userId, userId);
        Collection<RequestDto> usersRequests = usersService.getUserRequests(userId);
        log.info("Отправлен GET/users/{userId}/requests ответ: " + usersRequests);
        return usersRequests;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto saveUserRequest(@PathVariable Integer userId,
                                      @RequestParam Integer eventId) {
        log.info("Пришел GET/users/{}/requests запрос с параметром eventId = {} ", userId, eventId);
        if (eventId == null) {
            throw new ValidationException("Ошибка! Отсутствует параметр eventId");
        }
        RequestDto savedUserRequest = usersService.saveUserRequest(userId, eventId);
        log.info("Отправлен GET/users/{}/requests ответ c телом: " + savedUserRequest, userId);
        return savedUserRequest;
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancelUserRequest(@PathVariable Integer userId,
                                        @PathVariable Integer requestId) {
        log.info("Пришел PATCH/users/{}/requests/{}/cancel запрос", userId, requestId);
        RequestDto savedUserRequest = usersService.cancelUserRequest(userId, requestId);
        log.info("Отправлен PATCH/users/{}/requests/{}/cancel ответ", userId, requestId);
        return savedUserRequest;
    }
}
