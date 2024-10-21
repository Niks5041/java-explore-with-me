package ru.practicum.events.publicGroup.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.model.Sort;
import ru.practicum.events.service.EventsService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class EventsController {

    private final EventsService eventsService;

    @GetMapping
    public  Collection<EventFullDto> getEvents(@RequestParam String text,
                                               @RequestParam List<Integer> categoriesList,
                                               @RequestParam Boolean paid,
                                               @RequestParam LocalDateTime rangeStart,
                                               @RequestParam LocalDateTime rangeEnd,
                                               @RequestParam Boolean onlyAvailable,
                                               @RequestParam Sort sort,
                                               @RequestParam(defaultValue = "0") Integer from,
                                               @RequestParam(defaultValue = "10") Integer size) {
        log.info("Пришел GET/events запрос с параметрами" +
                        " text = {}, categoriesList = {}, paid = {}, rangeStart = {}, rangeEnd = {}, onlyAvailable = {}, sort = {}, from = {}, size = {}",
                text, categoriesList, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        Collection<EventFullDto> eventDtos = eventsService.getEvents(text, categoriesList, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        log.info("Отправлен GET/admin/events ответ c телом: " + eventDtos);
        return eventDtos;
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable Integer eventId) {
        log.info("Пришел GET/events/{} запрос с ID: ", eventId);
        EventFullDto eventDto = eventsService.getEventById(eventId);
        log.info("Отправлен GET/events ответ c телом: " + eventDto);
        return eventDto;
    }
}
