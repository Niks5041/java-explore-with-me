package ru.practicum.events.service;


import ru.practicum.events.dto.*;
import ru.practicum.events.model.Sort;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EventsService {
    Collection<EventFullDto> getEventsByAdmin(List<Integer> usersList,
                                        List<String> statesList,
                                        List<Integer> categoriesList,
                                        String rangeStart,
                                        String rangeEnd,
                                        Integer from,
                                        Integer size);

    EventFullDto updateEventByIdByAdmin(Integer eventId,
                                 UpdateEventAdminRequest updateEventAdminRequest);

    Collection<EventFullDto> getEvents(String text,
                                       List<Integer> categoriesList,
                                       Boolean paid,
                                       LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd,
                                       Boolean onlyAvailable,
                                       Sort sort,
                                       Integer from,
                                       Integer size);

    EventFullDto getEventById(Integer eventId);

    Collection<EventFullDto> getUserEvents(Integer userId, Integer from, Integer size);

    EventFullDto saveUserEvent(Integer userId, NewEventDto newEventDto);

    EventFullDto getUserEventById(Integer userId,
                                  Integer eventId);

    EventFullDto updateUserEventById(Integer userId,
                                     Integer eventId,
                                     NewEventDto newEventDto);

    Collection<EventFullDto> getUserEventRequests(Integer userId,
                                                  Integer eventId);

    EventRequestStatusUpdateResult updateUserEventRequestStatus(Integer userId,
                                                                Integer eventId,
                                                                EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);
}
