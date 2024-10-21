package ru.practicum.params;


import ru.practicum.events.dto.UpdateEventAdminRequest;
import ru.practicum.events.dto.UpdateEventUserRequest;

public record EventUpdateParams(
        Long userId,
        UpdateEventUserRequest updateEventUserRequest,
        UpdateEventAdminRequest updateEventAdminRequest
) {
}
