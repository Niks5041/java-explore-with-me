package ru.practicum.users.adminGroup.controller;

public record AdminUsersGetAllParams(
        Long[] ids,
        int from,
        int size
) {
}
