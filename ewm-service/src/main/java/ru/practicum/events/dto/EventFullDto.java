package ru.practicum.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.practicum.categories.model.CategoryDto;
import ru.practicum.events.model.Location;
import ru.practicum.events.model.enums.EventState;
import ru.practicum.users.dto.UserShortDto;

import java.time.LocalDateTime;

public record EventFullDto(
        String annotation,
        CategoryDto category,
        long confirmedRequests,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
        LocalDateTime createdOn,
        String description,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
        LocalDateTime eventDate,
        Long id,
        UserShortDto initiator,
        Location location,
        boolean paid,

        Integer participantLimit,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
        LocalDateTime publishedOn,

        boolean requestModeration,

        EventState state,

        String title,

        long views
) {

}
