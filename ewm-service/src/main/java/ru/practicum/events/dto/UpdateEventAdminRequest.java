package ru.practicum.events.dto;

import lombok.Data;
import ru.practicum.categories.model.CategoryDto;
import ru.practicum.events.model.Location;
import ru.practicum.events.model.StateAction;

import java.time.LocalDateTime;

@Data
public class UpdateEventAdminRequest {
    private String annotation;
    private CategoryDto category;
    private String description;
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateAction stateAction;
    private String title;
}
