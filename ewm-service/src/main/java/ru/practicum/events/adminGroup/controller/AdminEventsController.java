package ru.practicum.events.adminGroup.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.UpdateEventAdminRequest;
import ru.practicum.events.service.EventsService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Slf4j
public class AdminEventsController {

    private final EventsService eventsService;

    @GetMapping
    public  Collection<EventFullDto> getEventsByAdmin(@RequestParam List<Integer> usersList,
                                               @RequestParam List<String> statesList,
                                               @RequestParam List<Integer> categoriesList,
                                               @RequestParam String rangeStart,
                                               @RequestParam String rangeEnd,
                                               @RequestParam(defaultValue = "0") Integer from,
                                               @RequestParam(defaultValue = "10") Integer size) {
        log.info("Пришел GET/admin/events запрос с параметрами" +
                        " usersList = {}, statesList = {}, categoriesList = {}, rangeStart = {}, rangeEnd = {}, from = {}, size = {}",
                usersList, statesList, categoriesList, rangeStart, rangeEnd, from, size);
        Collection<EventFullDto> eventDtos = eventsService.getEventsByAdmin(usersList, statesList, categoriesList, rangeStart, rangeEnd, from, size);
        log.info("Отправлен GET/admin/events ответ c телом: " + eventDtos);
        return eventDtos;
    }

    @PatchMapping("/{eventId}")
    public EventFullDto getEventById(@RequestBody UpdateEventAdminRequest updateEventAdminRequest,
                                     @PathVariable Integer eventId) {
        log.info("Пришел PATCH/admin/events/{} запрос с телом: " + updateEventAdminRequest, eventId);
        EventFullDto eventDto = eventsService.updateEventByIdByAdmin(eventId, updateEventAdminRequest);
        log.info("Отправлен PATCH/admin/events ответ c телом: " + eventDto);
        return eventDto;
    }
}
