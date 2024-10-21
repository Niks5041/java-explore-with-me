package ru.practicum.events.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import ru.practicum.categories.model.CategoryDto;
import ru.practicum.events.model.Location;
import ru.practicum.users.dto.UserShortDto;

import java.time.LocalDateTime;

public record EventShortDto(
        String annotation,
        CategoryDto category,
        Long confirmedRequests,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
        LocalDateTime createOn,
        String description,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
        LocalDateTime eventDate,
        Long id,
        UserShortDto initiator,
        Location location,
        boolean paid,
        String title,
        Long views
) {
}
