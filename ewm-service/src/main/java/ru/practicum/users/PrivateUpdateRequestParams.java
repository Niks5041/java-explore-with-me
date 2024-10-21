package ru.practicum.users;

import ru.practicum.events.dto.EventRequestStatusUpdateRequest;

public record PrivateUpdateRequestParams(
        long userId,
        long eventId,
        EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest
) {
}
