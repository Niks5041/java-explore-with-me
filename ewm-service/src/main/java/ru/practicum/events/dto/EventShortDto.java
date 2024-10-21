package ru.practicum.events.dto;


import lombok.Data;
import ru.practicum.categories.model.CategoryDto;
import ru.practicum.users.dto.UserDto;

import java.time.LocalDateTime;

@Data
public class EventShortDto {
    private String annotation;
    private CategoryDto category;
    private Integer confirmedRequests;
    private LocalDateTime eventDate;
    private Integer id;
    private UserDto initiator;
    private Boolean paid;
    private String title;
    private Integer views;
}
