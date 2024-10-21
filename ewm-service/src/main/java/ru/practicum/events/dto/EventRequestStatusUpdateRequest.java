package ru.practicum.events.dto;


import lombok.Data;
import ru.practicum.events.model.State;

import java.util.List;

@Data
public class EventRequestStatusUpdateRequest {
     private List<Integer> requestIds;
     private State status;
}
