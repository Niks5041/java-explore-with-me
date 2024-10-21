package ru.practicum.compilations.dto.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.model.Compilation;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.model.Event;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CompilationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "events", source = "events")
    Compilation toCompilation(final NewCompilationDto compilationDto, final List<Event> events);

    CompilationDto toCompilationDto(final Compilation compilation, List<EventShortDto> list);
}
