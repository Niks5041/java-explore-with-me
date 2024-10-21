package ru.practicum.events.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.events.dao.EventsRepository;
import ru.practicum.events.dto.*;
import ru.practicum.events.dto.mapper.EventsMapper;
import ru.practicum.events.model.Sort;
import ru.practicum.exceptions.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class EventsServiceImpl implements EventsService {

    EventsRepository eventsRepository;

    @Override
    public Collection<EventFullDto> getEventsByAdmin(List<Integer> usersList,
                                              List<String> statesList,
                                              List<Integer> categoriesList,
                                              String rangeStart,
                                              String rangeEnd,
                                              Integer from,
                                              Integer size) {
        log.info("Пришел запрос в сервис Events с параметрами" +
                        " usersList = {}, statesList = {}, categoriesList = {}, rangeStart = {}, rangeEnd = {}, from = {}, size = {}",
                usersList, statesList, categoriesList, rangeStart, rangeEnd, from, size);

        Collection<EventFullDto> eventFullDtos = eventsRepository.findAll().stream()
                .map(EventsMapper.INSTANCE::toEventFullDto)
                .toList();

        return eventFullDtos;          // дописать запрос в бд
    }

    @Override
    public EventFullDto updateEventByIdByAdmin(Integer eventId,
                                    UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("Пришел запрос на обновление ивента в сервис Events с ID = {} и телом: "
                + eventId, updateEventAdminRequest);

        EventFullDto existEvent = EventsMapper.INSTANCE.toEventFullDto(eventsRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Ошибка! Ивент не найден")));

        if (updateEventAdminRequest.getAnnotation() != null) {
            existEvent.setAnnotation(updateEventAdminRequest.getAnnotation());
        }
        if (updateEventAdminRequest.getCategory() != null) {
            existEvent.setCategory(updateEventAdminRequest.getCategory());
        }
        if (updateEventAdminRequest.getDescription() != null) {
            existEvent.setDescription(updateEventAdminRequest.getDescription());
        }
        if (updateEventAdminRequest.getEventDate() != null) {
            existEvent.setEventDate(updateEventAdminRequest.getEventDate());
        }
        if (updateEventAdminRequest.getLocation() != null) {
            existEvent.setLocation(updateEventAdminRequest.getLocation());
        }
        if (updateEventAdminRequest.getPaid() != null) {
            existEvent.setPaid(updateEventAdminRequest.getPaid());
        }
        if (updateEventAdminRequest.getParticipantLimit() != null) {
            existEvent.setParticipantLimit(updateEventAdminRequest.getParticipantLimit());
        }
        if (updateEventAdminRequest.getRequestModeration() != null) {
            existEvent.setRequestModeration(updateEventAdminRequest.getRequestModeration());
        }
        if (updateEventAdminRequest.getStateAction() != null) {
            existEvent.setState(null);
        }
        if (updateEventAdminRequest.getTitle() != null) {
            existEvent.setTitle(updateEventAdminRequest.getTitle());
        }

        eventsRepository.save(EventsMapper.INSTANCE.toEvent(existEvent));
        return existEvent;
    }

    @Override
    public Collection<EventFullDto> getEvents(String text,
                                              List<Integer> categoriesList,
                                              Boolean paid,
                                              LocalDateTime rangeStart,
                                              LocalDateTime rangeEnd,
                                              Boolean onlyAvailable,
                                              Sort sort,
                                              Integer from,
                                              Integer size) {
        log.info("Пришел запрос в сервис Events с параметрами" +
                        " text = {}, categoriesList = {}, paid = {}, rangeStart = {}, rangeEnd = {}, onlyAvailable = {}, sort = {}, from = {}, size = {}",
                text, categoriesList, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);

        Collection<EventFullDto> eventFullDtos = eventsRepository.findAll().stream()
                .map(EventsMapper.INSTANCE::toEventFullDto)
                .toList();

        return eventFullDtos;          // дописать запрос в бд
    }

    @Override
    public EventFullDto getEventById(Integer eventId) {
        log.info("Пришел запрос на получение ивента в сервис Events с ID =  " + eventId);

        return EventsMapper.INSTANCE.toEventFullDto(eventsRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Ошибка! Ивент не найден")));
    }

    //------------------------------------------------------------------UserEvents

    @Override
    public Collection<EventFullDto> getUserEvents(Integer userId, Integer from, Integer size) {
        log.info("Пришел запрос в сервис Events на получение ивентов пользователя с параметрами userId = {}, from = {}, size = {}",
                userId, from, size);

        Collection<EventFullDto> eventFullDtos = eventsRepository.findAll().stream()
                .map(EventsMapper.INSTANCE::toEventFullDto)
                .toList();

        return eventFullDtos;          // дописать запрос в бд
    }

    @Override
    public EventFullDto saveUserEvent(Integer userId, NewEventDto newEventDto) {
        log.info("Пришел запрос в сервис Events на сохранение ивента пользователя с userId = {} и телом: {}",
                userId, newEventDto);

        return EventsMapper.INSTANCE.toEventFullDto(eventsRepository.save(EventsMapper.INSTANCE.toEvent(newEventDto)));        // дописать запрос в бд
    }

    @Override
    public EventFullDto getUserEventById(Integer userId, Integer eventId) {
        log.info("Пришел запрос в сервис Events на получение ивента пользователя с userId = {} и eventId = {}",
                userId, eventId);

        return EventsMapper.INSTANCE.toEventFullDto(eventsRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Ошибка! Ивент не найден")));
    }

    @Override
    public EventFullDto updateUserEventById(Integer userId, Integer eventId, NewEventDto newEventDto) {
        log.info("Пришел запрос в сервис Events на обновление ивента пользователя с userId = {}, eventId = {} и телом: {}",
                userId, eventId, newEventDto);

        EventFullDto existEvent = EventsMapper.INSTANCE.toEventFullDto(eventsRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Ошибка! Ивент не найден")));

        if (newEventDto.getAnnotation() != null) {
            existEvent.setAnnotation(newEventDto.getAnnotation());
        }
        if (newEventDto.getCategory() != null) {
            existEvent.setCategory(newEventDto.getCategory());
        }
        if (newEventDto.getDescription() != null) {
            existEvent.setDescription(newEventDto.getDescription());
        }
        if (newEventDto.getEventDate() != null) {
            existEvent.setEventDate(newEventDto.getEventDate());
        }
        if (newEventDto.getLocation() != null) {
            existEvent.setLocation(newEventDto.getLocation());
        }
        if (newEventDto.getPaid() != null) {
            existEvent.setPaid(newEventDto.getPaid());
        }
        if (newEventDto.getParticipantLimit() != null) {
            existEvent.setParticipantLimit(newEventDto.getParticipantLimit());
        }
        if (newEventDto.getRequestModeration() != null) {
            existEvent.setRequestModeration(newEventDto.getRequestModeration());
        }
        if (newEventDto.getTitle() != null) {
            existEvent.setTitle(newEventDto.getTitle());
        }

        eventsRepository.save(EventsMapper.INSTANCE.toEvent(existEvent));
        return existEvent;
    }

    @Override
    public Collection<EventFullDto> getUserEventRequests(Integer userId, Integer eventId) {
        log.info("Пришел запрос в сервис Events на получение заявок пользователя с userId = {} и eventId = {}",
                userId, eventId);

        Collection<EventFullDto> eventFullDtos = eventsRepository.findAll().stream()
                .map(EventsMapper.INSTANCE::toEventFullDto)
                .toList();

        return eventFullDtos;          // дописать запрос в бд
    }

    @Override
    public EventRequestStatusUpdateResult updateUserEventRequestStatus(Integer userId,
                                                                       Integer eventId,
                                                                       EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        log.info("Пришел запрос в сервис Events на обновление статуса заявок пользователя с userId = {}, eventId = {} и телом: {}",
                userId, eventId, eventRequestStatusUpdateRequest);

        return null;          // дописать запрос в бд
    }


}

