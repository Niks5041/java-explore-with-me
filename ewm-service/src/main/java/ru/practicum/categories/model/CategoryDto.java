package ru.practicum.categories.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDto {
    private long id;
    @Pattern(regexp = "\\S+")
    @Size(max = 50, message = "Имя не более 50 символов")
    @NotNull
    private String name;
}
