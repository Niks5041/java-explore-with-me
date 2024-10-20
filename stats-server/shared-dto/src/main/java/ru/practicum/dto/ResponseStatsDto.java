package ru.practicum.dto;

import lombok.Data;

@Data
public class ResponseStatsDto {
    private String app;
    private String uri;
    private Integer hits;
}
