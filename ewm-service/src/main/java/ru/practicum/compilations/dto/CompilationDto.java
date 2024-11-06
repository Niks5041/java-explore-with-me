package ru.practicum.compilations.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.events.dto.EventShortDto;
import java.util.List;

@Data
@AllArgsConstructor
public class CompilationDto {
    private Long id;
    private List<EventShortDto> events;
    private Boolean pinned;
    @NotBlank
    @Size(max = 50, message = "Имя не более 50 символов")
    private String title;
}