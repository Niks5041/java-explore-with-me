package ru.practicum.compilations.service;


import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;

import java.util.Collection;

public interface CompilationsService {
    Collection<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);
    Collection<CompilationDto> getCompilationsById(Integer comId);
    CompilationDto saveCompilation(NewCompilationDto compilationDto);
    CompilationDto updateCompilationById(Integer comId, NewCompilationDto compilationDto);
    void deleteCompilationById(Integer comId);
}
