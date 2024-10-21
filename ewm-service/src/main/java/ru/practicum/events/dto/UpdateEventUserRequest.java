package ru.practicum.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import ru.practicum.events.model.Location;
import ru.practicum.events.model.enums.StateAction;

import java.time.LocalDateTime;

public record UpdateEventUserRequest(

        @Size(min = 20, max = 2000)
        String annotation,

        Long category,

        @Size(min = 20, max = 7000)
        String description,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
        LocalDateTime eventDate,

        Location location,

        Boolean paid,

        @PositiveOrZero
        Integer participantLimit,

        String publishedOn,

        Boolean requestModeration,

        StateAction stateAction,

        @Size(min = 3, max = 120)
        String title

) {
}
