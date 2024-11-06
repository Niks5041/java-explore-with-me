package ru.practicum.events.dto;

public record LocationDto(
        long id,
        Float lat,
        Float lon,
        Long likes
) {
}