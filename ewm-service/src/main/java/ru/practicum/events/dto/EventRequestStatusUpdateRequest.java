package ru.practicum.events.dto;

import jakarta.validation.constraints.NotNull;
import ru.practicum.users.model.RequestStatus;

import java.util.List;

public record EventRequestStatusUpdateRequest(

        List<Long> requestIds,

        @NotNull
        RequestStatus status

) {
}
