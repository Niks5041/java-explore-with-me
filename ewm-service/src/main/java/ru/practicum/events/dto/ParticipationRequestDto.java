package ru.practicum.events.dto;


import ru.practicum.users.model.RequestStatus;

import java.time.LocalDateTime;

public record ParticipationRequestDto(

        LocalDateTime created,

        Long event,

        Long id,

        Long requester,

        RequestStatus status
) {
}
