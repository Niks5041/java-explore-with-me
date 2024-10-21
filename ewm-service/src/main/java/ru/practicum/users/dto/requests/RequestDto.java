package ru.practicum.users.dto.requests;

import lombok.Data;
import ru.practicum.events.model.enums.Status;

import java.time.LocalDateTime;

@Data
public class RequestDto {
    LocalDateTime created;
    Integer event;
    Integer id;
    Integer requester;
    Status status;
}
