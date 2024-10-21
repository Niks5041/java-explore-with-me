package ru.practicum.events.privateGroup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventRequestStatusUpdateRequest;
import ru.practicum.events.dto.EventRequestStatusUpdateResult;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.service.EventsService;
import ru.practicum.users.dto.NewUserRequestDto;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.service.UserService;

import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
public class PrivateUsersEventsController {

    private final EventsService eventsService;

    @GetMapping
    public Collection<EventFullDto> getUsersEvents(@RequestParam Integer userId,
                                                   @RequestParam(defaultValue = "0") Integer from,
                                                   @RequestParam(defaultValue = "10") Integer size) {
        log.info("Пришел GET/users/{userId}/events запрос с параметрами userId = {}, from = {}, size = {}",
                userId, from, size);
        Collection<EventFullDto> events = eventsService.getUserEvents(userId, from, size);
        log.info("Отправлен /users/{userId}/events ответ: " + events);
        return events;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto saveUserEvents(@RequestParam Integer userId,
                                       @RequestBody NewEventDto newEventDto) {
        log.info("Пришел GET/users/{userId}/events запрос с userId = {} и телом: {}",
                userId, newEventDto);
        EventFullDto event = eventsService.saveUserEvent(userId, newEventDto);
        log.info("Отправлен GET/users/{userId}/events запрос ответ c телом: " + event);
        return event;
    }

    @GetMapping("/{eventId}")
    public EventFullDto getUserEventById(@RequestParam Integer userId,
                                         @RequestParam Integer eventId) {
        log.info("Пришел GET/users/{userId}/events/{eventId} запрос с  userId = {} и eventId = {}", userId, eventId);
        EventFullDto event = eventsService.getUserEventById(userId, eventId);
        log.info("Отправлен GET/users/{userId}/events/{eventId} запрос ответ c телом: " + event);
        return event;
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateUserEventById(@RequestParam Integer userId,
                                        @RequestParam Integer eventId,
                                        @RequestBody NewEventDto newEventDto) {
        log.info("Пришел PATCH/users/{userId}/events/{eventId} запрос с  userId = {}, eventId = {} и телом: {}",
                userId, eventId, newEventDto);
        EventFullDto event = eventsService.updateUserEventById(userId, eventId, newEventDto);
        log.info("Отправлен PATCH/users/{userId}/events/{eventId} запрос ответ c телом: " + event);
        return event;
    }

    @GetMapping("/{eventId}/requests")
    public Collection<EventFullDto> getUserEventRequests(@RequestParam Integer userId,
                                                         @RequestParam Integer eventId) {
        log.info("Пришел GET/users/{userId}/events/{eventId}/requests запрос с  userId = {} и eventId = {}",
                userId, eventId);
        Collection<EventFullDto> requests = eventsService.getUserEventRequests(userId, eventId);
        log.info("Отправлен GET/users/{userId}/events/{eventId}/requests запрос ответ c телом: " + requests);
        return requests;
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateUserEventRequestStatus(@RequestParam Integer userId,
                                                                      @RequestParam Integer eventId,
                                                                      @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        log.info("Пришел PATCH/users/{userId}/events/{eventId}/requests запрос с  userId = {}, eventId = {} и телом: {}",
                userId, eventId, eventRequestStatusUpdateRequest);
        EventRequestStatusUpdateResult result = eventsService.updateUserEventRequestStatus(userId, eventId, eventRequestStatusUpdateRequest);
        log.info("Отправлен PATCH/users/{userId}/events/{eventId}/requests запрос ответ c телом: " + result);
        return result;
    }
}
