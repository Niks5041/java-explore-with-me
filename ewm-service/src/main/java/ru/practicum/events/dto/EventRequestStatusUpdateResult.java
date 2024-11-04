package ru.practicum.events.dto;

import java.util.List;

public record EventRequestStatusUpdateResult(

        List<ParticipationRequestDto> confirmedRequests,

        List<ParticipationRequestDto> rejectedRequests

) {
}
