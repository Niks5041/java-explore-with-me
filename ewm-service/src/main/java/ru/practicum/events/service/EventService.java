package ru.practicum.events.service;

import ru.practicum.HitDto;
import ru.practicum.events.dto.*;
import ru.practicum.params.EventGetByIdParams;
import ru.practicum.params.EventUpdateParams;
import ru.practicum.params.search.EventSearchParams;

import java.util.List;

public interface EventService {
    EventFullDto create(long userId, NewEventDto newEventDto);

    EventFullDto getById(EventGetByIdParams params, HitDto hitDto);

    EventFullDto update(long eventId, EventUpdateParams updateParams);

    List<EventFullDto> getAllByAdmin(EventSearchParams searchParams);

    List<EventShortDto> getAllByInitiator(EventSearchParams searchParams);

    List<EventShortDto> getAllByPublic(EventSearchParams searchParams, HitDto hitDto);
}