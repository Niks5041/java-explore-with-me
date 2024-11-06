package ru.practicum.events.controller.prvt;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.dto.*;
import ru.practicum.events.service.EventService;
import ru.practicum.params.EventGetByIdParams;
import ru.practicum.params.EventUpdateParams;
import ru.practicum.params.search.EventSearchParams;
import ru.practicum.params.search.PrivateSearchParams;
import ru.practicum.users.controller.prvt.PrivateUpdateRequestParams;
import ru.practicum.users.service.RequestService;

import java.util.List;


@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
public class PrivateUsersEventsController {

    private final EventService eventService;
    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@PathVariable long userId, @Valid @RequestBody NewEventDto newEventDto) {
        log.info("==> POST. /users/{userId}/events " +
                "Создание события {} пользователем id: {}", newEventDto, userId);
        EventFullDto receivedEventDto = eventService.create(userId, newEventDto);
        log.info("<== POST. /users/{userId}/events " +
                "Создано событие {} c телом ответа: {}", receivedEventDto.id(), receivedEventDto);
        return receivedEventDto;
    }

    @GetMapping
    public List<EventShortDto> getAll(
            @PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("==> GET. /users/{userId}/events " +
                "Получение всех событий для пользователя id {}: from {}, size {}", userId, from, size);
        EventSearchParams searchParams = new EventSearchParams();
        searchParams.setPrivateSearchParams(new PrivateSearchParams(userId));
        searchParams.setFrom(from);
        searchParams.setSize(size);
        List<EventShortDto> receivedEventsDtoList =
                eventService.getAllByInitiator(searchParams);

        log.info("<== GET. /users/{userId}/events " +
                "Получены события для пользователя c id {} c телом ответа: {}", userId, receivedEventsDtoList);
        return receivedEventsDtoList;
    }

    @GetMapping("/{eventId}")
    public EventFullDto getById(@PathVariable long userId, @PathVariable long eventId) {
        log.info("==> GET. /users/{userId}/events/{eventId} " +
                "Получить событие id: {}, by user with id: {}", eventId, userId);
        EventFullDto receivedEventDto = eventService.getById(new EventGetByIdParams(userId, eventId), null);
        log.info("<== GET. /users/{userId}/events/{eventId} " +
                "Вернули событие c id {} c телом ответа: {}", eventId, receivedEventDto);
        return receivedEventDto;
    }

    @PatchMapping("/{eventId}")
    public EventFullDto update(@PathVariable long userId,
                               @PathVariable long eventId,
                               @Valid @RequestBody UpdateEventUserRequest updateEventDto) {
        log.info("==> PATCH. /users/{userId}/events/{eventId} " +
                "Обновить событие id: {}, пользователем id: {} {}", eventId, userId, updateEventDto);

        EventFullDto receivedEventDto = eventService.update(
                eventId, new EventUpdateParams(userId, updateEventDto, null));
        log.info("<== PATCH. /users/{userId}/events/{eventId} " +
                        "Обновлено событие id: {}, пользователем id: {} c телом ответа: {}",
                eventId, userId, receivedEventDto);
        return receivedEventDto;
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getAllRequestsForOwnEvent(
            @PathVariable long userId,
            @PathVariable long eventId) {
        log.info("==> GET. /users/{userId}/events/{eventId}/requests " +
                "Запрос на событие id: {}, от пользователя id: {}", eventId, userId);

        List<ParticipationRequestDto> receivedRequestsDtoList
                = requestService.getAllForOwnEvent(userId, eventId);

        log.info("<== GET. /users/{userId}/events/{eventId}/requests " +
                "Отправлен запрос на событие c id: {} от пользователя c id: {}, c телом ответа: {}",
                eventId, userId, receivedRequestsDtoList);

        return receivedRequestsDtoList;
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequestStatus(
            @PathVariable long userId,
            @PathVariable long eventId,
            @RequestBody @Valid EventRequestStatusUpdateRequest updateRequestStatusDto) {

        log.info("==> PATCH. /users/{userId}/events/{eventId}/requests " +
                "Изменение запроса на событие: {} от пользователя id: {}", eventId, userId);
        EventRequestStatusUpdateResult eventUpdateResult =
                requestService.updateStatus(new PrivateUpdateRequestParams(userId, eventId, updateRequestStatusDto));
        log.info("<== PATCH. /users/{userId}/events/{eventId}/requests " +
                "Изменен запрос на событие id: {} от пользователя id: {}, c телом ответа: {}",
                eventId, userId, eventUpdateResult);
        return eventUpdateResult;
    }

}
