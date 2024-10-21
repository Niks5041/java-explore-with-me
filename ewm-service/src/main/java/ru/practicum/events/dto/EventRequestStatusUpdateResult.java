package ru.practicum.events.dto;

import ru.practicum.users.dto.requests.ParticipationRequestDto;
import ru.practicum.users.dto.requests.RequestDto;

import java.util.List;

public class EventRequestStatusUpdateResult {
     private List<ParticipationRequestDto> confirmedRequests;
     private List<ParticipationRequestDto> rejectedRequests;
}
