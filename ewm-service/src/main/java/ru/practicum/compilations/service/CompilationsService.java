package ru.practicum.compilations.service;


import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.dto.UpdateCompilationRequestDto;

import java.util.List;

public interface CompilationsService {

    List<CompilationDto> getAll(Boolean pinned, int from, int size);

    CompilationDto getById(Long categoryId);

    void delete(long compilationId);

    CompilationDto createCompilation(NewCompilationDto dto);

    CompilationDto updateCompilation(Long id, UpdateCompilationRequestDto dto);
}
