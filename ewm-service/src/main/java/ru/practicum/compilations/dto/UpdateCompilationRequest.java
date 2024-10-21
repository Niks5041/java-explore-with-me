package ru.practicum.compilations.dto;

import java.util.Collection;

public class UpdateCompilationRequest {
    private Collection<Integer> events;
    private Boolean pinned;
    private String title;
}
