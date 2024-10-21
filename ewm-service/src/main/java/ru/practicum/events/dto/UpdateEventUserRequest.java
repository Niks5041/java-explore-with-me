package ru.practicum.events.dto;

import lombok.Data;
import ru.practicum.events.model.Location;

@Data
public class UpdateEventUserRequest {
    private String annotation;
    private Integer category;
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String stateAction;
    private String title;
}
