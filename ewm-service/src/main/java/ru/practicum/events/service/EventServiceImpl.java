package ru.practicum.events.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.HitDto;
import ru.practicum.HitStatDto;
import ru.practicum.categories.dao.CategoryRepository;
import ru.practicum.categories.model.Category;
import ru.practicum.client.StatClient;
import ru.practicum.events.dao.EventRepository;
import ru.practicum.events.dao.LocationRepository;
import ru.practicum.events.dto.*;
import ru.practicum.events.dto.mapper.EventMapper;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.Location;
import ru.practicum.events.model.enums.EventState;
import ru.practicum.events.model.enums.StateAction;
import ru.practicum.exceptions.AccessException;
import ru.practicum.exceptions.ConflictException;
import ru.practicum.exceptions.IncorrectValueException;
import ru.practicum.exceptions.NotFoundException;
import ru.practicum.params.EventGetByIdParams;
import ru.practicum.params.EventUpdateParams;
import ru.practicum.params.search.EventSearchParams;
import ru.practicum.params.search.PublicSearchParams;
import ru.practicum.users.dao.RequestRepository;
import ru.practicum.users.dao.UserRepository;
import ru.practicum.users.model.RequestStatus;
import ru.practicum.users.model.User;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.practicum.events.model.QEvent.event;


@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;

    private final StatClient statClient;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public EventFullDto create(long userId, NewEventDto newEventDto) {
        User initiator = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
        Category category = categoryRepository.findById(newEventDto.category())
                .orElseThrow(() -> new NotFoundException("Category with id " + newEventDto.category() + " not found"));
        Location location = locationRepository.save(newEventDto.location());
        Event event = eventMapper.newEventDtoToEvent(newEventDto, initiator, category, location, LocalDateTime.now());
        Event savedEvent = eventRepository.save(event);
        return eventMapper.eventToEventFullDto(savedEvent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getAllByInitiator(EventSearchParams searchParams) {

        long initiatorId = searchParams.getPrivateSearchParams().getInitiatorId();
        User user = userRepository.findById(initiatorId)
                .orElseThrow(() -> new NotFoundException("User with id " + initiatorId + " not found"));
        Pageable page = PageRequest.of(searchParams.getFrom(), searchParams.getSize());

        return eventRepository.findAllByInitiatorId(initiatorId, page).stream()
                .map(eventMapper::eventToEventShortDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getAllByPublic(EventSearchParams searchParams, HitDto hitDto) {

        Pageable page = PageRequest.of(searchParams.getFrom(), searchParams.getSize());

        BooleanExpression booleanExpression = event.isNotNull();

        PublicSearchParams publicSearchParams = searchParams.getPublicSearchParams();

        if (publicSearchParams.getText() != null) { //наличие поиска по тексту
            booleanExpression = booleanExpression.andAnyOf(
                    event.annotation.likeIgnoreCase(publicSearchParams.getText()),
                    event.description.likeIgnoreCase(publicSearchParams.getText())
            );
        }

        if (publicSearchParams.getCategories() != null) { // наличие поиска по категориям
            booleanExpression = booleanExpression.and(
                    event.category.id.in((publicSearchParams.getCategories())));
        }

        if (publicSearchParams.getPaid() != null) { // наличие поиска по категориям
            booleanExpression = booleanExpression.and(
                    event.paid.eq(publicSearchParams.getPaid()));
        }

        LocalDateTime rangeStart = publicSearchParams.getRangeStart();
        LocalDateTime rangeEnd = publicSearchParams.getRangeEnd();

        if (rangeStart != null && rangeEnd != null) { // наличие поиска дате события
            booleanExpression = booleanExpression.and(
                    event.eventDate.between(rangeStart, rangeEnd)
            );
        } else if (rangeStart != null) {
            booleanExpression = booleanExpression.and(
                    event.eventDate.after(rangeStart)
            );
            rangeEnd = rangeStart.plusYears(100);
        } else if (publicSearchParams.getRangeEnd() != null) {
            booleanExpression = booleanExpression.and(
                    event.eventDate.before(rangeEnd)
            );
            rangeStart = LocalDateTime.parse(LocalDateTime.now().format(dateTimeFormatter), dateTimeFormatter);
        }

        if (rangeEnd == null && rangeStart == null) {
            booleanExpression = booleanExpression.and(
                    event.eventDate.after(LocalDateTime.now())
            );
            rangeStart = LocalDateTime.parse(LocalDateTime.now().format(dateTimeFormatter), dateTimeFormatter);
            rangeEnd = rangeStart.plusYears(100);
        }

        List<Event> eventListBySearch =
                eventRepository.findAll(booleanExpression, page).stream().toList();

        statClient.saveHit(hitDto);


        for (Event event : eventListBySearch) {
            List<HitStatDto> hitStatDtoList = statClient.getStats(
                    rangeStart.format(dateTimeFormatter),
                    rangeEnd.format(dateTimeFormatter),
                    List.of("/event/" + event.getId()),
                    false);
            Long view = 0L;
            for (HitStatDto hitStatDto : hitStatDtoList) {
                view += hitStatDto.getHits();
            }
            event.setViews(view);
            event.setConfirmedRequests(
                    requestRepository.countByStatusAndEventId(RequestStatus.CONFIRMED, event.getId()));
        }

        return eventListBySearch.stream()
                .map(eventMapper::eventToEventShortDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventFullDto> getAllByAdmin(EventSearchParams searchParams) {
        Pageable page = PageRequest.of(
                searchParams.getFrom(), searchParams.getSize());

        BooleanExpression booleanExpression = event.isNotNull();

        if (searchParams.getAdminSearchParams().getUsers() != null) {
            booleanExpression = booleanExpression.and(
                    event.initiator.id.in(searchParams.getAdminSearchParams().getUsers()));
        }

        if (searchParams.getAdminSearchParams().getCategories() != null) {
            booleanExpression = booleanExpression.and(
                    event.category.id.in(searchParams.getAdminSearchParams().getCategories()));
        }

        if (searchParams.getAdminSearchParams().getStates() != null) {
            booleanExpression = booleanExpression.and(
                    event.state.in(searchParams.getAdminSearchParams().getStates()));
        }

        LocalDateTime rangeStart = searchParams.getAdminSearchParams().getRangeStart();
        LocalDateTime rangeEnd = searchParams.getAdminSearchParams().getRangeEnd();

        if (rangeStart != null && rangeEnd != null) {
            booleanExpression = booleanExpression.and(
                    event.eventDate.between(rangeStart, rangeEnd));
        } else if (rangeStart != null) {
            booleanExpression = booleanExpression.and(
                    event.eventDate.after(rangeStart));
        } else if (rangeEnd != null) {
            booleanExpression = booleanExpression.and(
                    event.eventDate.before(rangeEnd));
        }

        List<Event> receivedEventList = eventRepository.findAll(booleanExpression, page).stream().toList();
        for (Event event : receivedEventList) {
            event.setConfirmedRequests(requestRepository.countByStatusAndEventId(RequestStatus.CONFIRMED, event.getId()));
        }

        return receivedEventList
                .stream()
                .map(eventMapper::eventToEventFullDto)
                .toList();

    }


    @Override
    @Transactional(readOnly = true)
    public EventFullDto getById(EventGetByIdParams params, HitDto hitDto) {
        Event receivedEvent;
        if (params.initiatorId() != null) {
            User user = userRepository.findById(params.initiatorId())
                    .orElseThrow(() -> new NotFoundException("User with id " + params.initiatorId() + " not found"));
            receivedEvent = eventRepository.findByInitiatorIdAndId(params.initiatorId(), params.eventId())
                    .orElseThrow(() -> new NotFoundException(
                            "Event with id " + params.eventId() +
                                    " created by user with id " + params.initiatorId() + " not found"));
        } else {
            receivedEvent = eventRepository.findById(params.eventId())
                    .orElseThrow(() -> new NotFoundException("Event with id " + params.eventId() + " not found"));
            statClient.saveHit(hitDto);

            List<HitStatDto> hitStatDtoList = statClient.getStats(
                    null, null, List.of("/events/" + params.eventId()), true
            );
            Long view = 0L;
            for (HitStatDto hitStatDto : hitStatDtoList) {
                view += hitStatDto.getHits();
            }
            receivedEvent.setViews(view);
            receivedEvent.setConfirmedRequests(
                    requestRepository.countByStatusAndEventId(RequestStatus.CONFIRMED, receivedEvent.getId()));
        }
        return eventMapper.eventToEventFullDto(receivedEvent);
    }

    @Override
    public EventFullDto update(long eventId, EventUpdateParams updateParams) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id " + eventId + " not found"));

        Event updatedEvent;

        if (updateParams.updateEventUserRequest() != null) { // private section
            User user = userRepository.findById(updateParams.userId())
                    .orElseThrow(() -> new NotFoundException("User with id " + updateParams.userId() + " not found"));

            if (updateParams.updateEventUserRequest().category() != null) {
                Category category = categoryRepository.findById(updateParams.updateEventUserRequest().category())
                        .orElseThrow(() -> new NotFoundException(
                                "Category with id " + updateParams.updateEventUserRequest().category() + " not found"));
                event.setCategory(category);
            }
            if (!updateParams.userId().equals(event.getInitiator().getId())) {
                throw new AccessException("User with id = " + updateParams.userId() + " do not initiate this event");
            }

            if (event.getState() != EventState.PENDING && event.getState() != EventState.CANCELED) {
                throw new ConflictException(
                        "User. Cannot update event: only pending or canceled events can be changed");
            }

            LocalDateTime eventDate = updateParams.updateEventUserRequest().eventDate();

            if (eventDate != null &&
                    eventDate.isBefore(LocalDateTime.now().plusHours(2))) {
                throw new IncorrectValueException(
                        "User. Cannot update event: event date must be not earlier then after 2 hours ");
            }

            StateAction stateAction = updateParams.updateEventUserRequest().stateAction();
            log.debug("State action received from params: {}", stateAction);

            if (stateAction != null) {
                switch (stateAction) {
                    case CANCEL_REVIEW -> event.setState(EventState.CANCELED);

                    case SEND_TO_REVIEW -> event.setState(EventState.PENDING);
                }
            }

            log.debug("Private. Событие до мапинга: {}", event);
            eventMapper.updateEventUserRequestToEvent(event, updateParams.updateEventUserRequest());
            log.debug("Private. Событие после мапинга для сохранения: {}", event);

        }

        if (updateParams.updateEventAdminRequest() != null) { // admin section

            if (updateParams.updateEventAdminRequest().category() != null) {
                Category category  = categoryRepository.findById(updateParams.updateEventAdminRequest().category())
                        .orElseThrow(() -> new NotFoundException(
                                "Category with id " + updateParams.updateEventAdminRequest().category() + " not found"));
                event.setCategory(category);
            }

            if (event.getState() != EventState.PENDING) {
                throw new ConflictException("Admin. Cannot update event: only pending events can be changed");
            }

            if (updateParams.updateEventAdminRequest().eventDate() != null &&
                    updateParams.updateEventAdminRequest().eventDate().isBefore(LocalDateTime.now().plusHours(1))) {
                throw new IncorrectValueException(
                        "Admin. Cannot update event: event date must be not earlier then after 2 hours ");
            }
            log.debug("Admin. Событие до мапинга: {}; {}", event.getId(), event.getState());
            eventMapper.updateEventAdminRequestToEvent(event, updateParams.updateEventAdminRequest());
            log.debug("Admin. Событие после мапинга для сохранения: {}, {}", event.getId(), event.getState());

        }
        event.setId(eventId);

        updatedEvent = eventRepository.save(event);

        log.debug("Событие возвращенное из базы: {} ; {}", event.getId(), event.getState());

        return eventMapper.eventToEventFullDto(updatedEvent);
    }


}

