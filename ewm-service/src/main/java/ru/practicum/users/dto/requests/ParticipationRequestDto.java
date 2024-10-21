package ru.practicum.users.dto.requests;

import lombok.Data;
import ru.practicum.events.model.State;

import java.time.LocalDateTime;

@Data
public class ParticipationRequestDto {
     private LocalDateTime created;
     private Integer event;
     private Integer id;
     private Integer requester;
     private State status;
}
