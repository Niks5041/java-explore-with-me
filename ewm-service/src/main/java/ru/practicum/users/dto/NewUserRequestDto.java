package ru.practicum.users.dto;

import lombok.Data;

@Data
public class NewUserRequestDto {
    private String email;
    private String name;
}
