package ru.practicum.users.controller.admin;

public record AdminUsersGetAllParams(
        Long[] ids,
        int from,
        int size
) {
}
