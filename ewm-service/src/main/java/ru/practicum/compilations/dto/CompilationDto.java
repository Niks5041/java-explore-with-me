package ru.practicum.compilations.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.events.dto.EventFullDto;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    private Collection<EventFullDto> events;
    private Integer id;
    private Boolean pinned;
    private String title;
}
