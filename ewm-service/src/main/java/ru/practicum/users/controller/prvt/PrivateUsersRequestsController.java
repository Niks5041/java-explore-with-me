package ru.practicum.users.controller.prvt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.dto.ParticipationRequestDto;
import ru.practicum.users.service.RequestService;

import java.util.List;


@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
public class PrivateUsersRequestsController {

    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto create(
            @PathVariable long userId,
            @RequestParam long eventId) {
        log.info("==> POST. /users/{userId}/requests " +
                "Создан запрос id: {} от пользователя id: {}", eventId, userId);
        ParticipationRequestDto receivedRequestDto = requestService.create(userId, eventId);
        log.info("<== POST. /users/{userId}/requests " +
                "Создан запрос {}: {}", receivedRequestDto.id(), receivedRequestDto);
        return receivedRequestDto;
    }

    @GetMapping
    public List<ParticipationRequestDto> getOwnRequests(
            @PathVariable long userId) {
        log.info("==> GET. /users/{userId}/requests " +
                "Получить все запросы для пользователя id: {} ", userId);
        List<ParticipationRequestDto> requestDtoList = requestService.getAllOwnRequests(userId);
        log.info("<== GET. /users/{userId}/requests " +
                "Вернули все запросы пользователя id: {} с телом: {}", userId, requestDtoList);
        return requestDtoList;
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancel(
            @PathVariable long userId,
            @PathVariable long requestId) {
        log.info("==> PATCH. /users/{userId}/requests/{requestId}/cancel" +
                "Отмена запроса id {} пользователем id: {} ", requestId, userId);
        ParticipationRequestDto receivedDto = requestService.cancel(userId, requestId);
        log.info("<== PATCH. /users/{userId}/requests/{requestId}/cancel" +
                "Отменен запрос id {} пользователем id: {} с телом ответа: {}", requestId, userId, receivedDto);
        return receivedDto;
    }
}
