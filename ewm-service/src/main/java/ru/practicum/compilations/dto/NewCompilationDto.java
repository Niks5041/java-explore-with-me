package ru.practicum.compilations.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NewCompilationDto {
    private List<Long> events;
    private boolean pinned = false;
    @NotBlank
    @Size(min = 1, max = 50, message = "Имя не более 50 символов")
    private String title;
}
