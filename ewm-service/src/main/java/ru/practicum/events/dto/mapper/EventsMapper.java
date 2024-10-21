package ru.practicum.events.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.dto.UpdateEventAdminRequest;
import ru.practicum.events.model.Events;

@Mapper
public interface EventsMapper {

    EventsMapper INSTANCE = Mappers.getMapper(EventsMapper.class);

    EventFullDto toEventFullDto(Events events);

    Events toEvent(EventFullDto eventFullDto);

    EventFullDto toEventFullDto(UpdateEventAdminRequest  updateEventAdminRequest);

    Events toEvent(NewEventDto newEventDto);
}
