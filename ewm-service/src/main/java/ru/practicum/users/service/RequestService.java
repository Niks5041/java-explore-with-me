package ru.practicum.users.service;

import ru.practicum.events.dto.EventRequestStatusUpdateResult;
import ru.practicum.events.dto.ParticipationRequestDto;
import ru.practicum.users.PrivateUpdateRequestParams;

import java.util.List;

public interface RequestService {

    ParticipationRequestDto create(long userId, long eventId);

    List<ParticipationRequestDto> getAllOwnRequests(long userId);

    ParticipationRequestDto cancel(long userId, long requestId);

    List<ParticipationRequestDto> getAllForOwnEvent(long userId, long eventId);

    EventRequestStatusUpdateResult updateStatus(PrivateUpdateRequestParams params);
}
