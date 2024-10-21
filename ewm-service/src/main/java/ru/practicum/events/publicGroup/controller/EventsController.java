package ru.practicum.events.publicGroup.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.HitDto;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.model.enums.EventState;
import ru.practicum.events.service.EventService;
import ru.practicum.exceptions.IncorrectValueException;
import ru.practicum.exceptions.NotFoundException;
import ru.practicum.params.EventGetByIdParams;
import ru.practicum.params.search.EventSearchParams;
import ru.practicum.params.search.PublicSearchParams;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class EventsController {

    private final EventService eventService;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @GetMapping
    public List<EventShortDto> getAll(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(required = false, defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            HttpServletRequest httpRequest) {
        log.info("==> GET /events Поиск событий по параметрам: " +
                        "text {}, categories: {}, paid {}, rangeStart: {}, rangeEnd: {}, available {}, from: {}, size: {}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, from, size);

        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new IncorrectValueException("rangeStart of event can't be after rangeEnd");
        }

        EventSearchParams eventSearchParams = new EventSearchParams();
        PublicSearchParams publicSearchParams = new PublicSearchParams();
        publicSearchParams.setText(text);
        publicSearchParams.setCategories(categories);
        publicSearchParams.setPaid(paid);

        publicSearchParams.setRangeStart(rangeStart);
        publicSearchParams.setRangeEnd(rangeEnd);

        eventSearchParams.setPublicSearchParams(publicSearchParams);
        eventSearchParams.setFrom(from);
        eventSearchParams.setSize(size);

        HitDto hitDto = new HitDto(
                null,
                "ewm-service",
                httpRequest.getRequestURI(),
                httpRequest.getRemoteAddr(),
                LocalDateTime.now().format(dateTimeFormatter));

        List<EventShortDto> eventShortDtoList = eventService.getAllByPublic(eventSearchParams, hitDto);
        log.info("<== GET /events Получен список событий размером {}",
                eventShortDtoList.size());
        return eventShortDtoList;
    }


    @GetMapping("/{id}")
    @Transactional
    public EventFullDto getById(
            @PathVariable Long id, HttpServletRequest httpRequest
    ) {
        log.info("==> GET /events/{}  Public getById", id);
        HitDto hitDto = new HitDto(
                null,
                "ewm-service",
                httpRequest.getRequestURI(),
                httpRequest.getRemoteAddr(),
                LocalDateTime.now().format(dateTimeFormatter));
        EventFullDto eventFullDto = eventService.getById(new EventGetByIdParams(null, id), hitDto);
        if (eventFullDto.state() != EventState.PUBLISHED) {
            throw new NotFoundException("Нет опубликованных событий с id " + id);
        }
        log.info("<== GET /events/{}  Public getById", id);
        return eventFullDto;
    }
}