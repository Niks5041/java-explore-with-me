package ru.practicum.compilations.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.compilations.dao.CompilationRepository;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.dto.UpdateCompilationRequestDto;
import ru.practicum.compilations.dto.mapper.CompilationMapper;
import ru.practicum.compilations.model.Compilation;
import ru.practicum.events.dao.EventRepository;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.mapper.EventMapper;
import ru.practicum.events.model.Event;
import ru.practicum.exceptions.NotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class CompilationsServiceImpl implements CompilationsService {

    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public List<CompilationDto> getAll(Boolean pinned, int from, int size) {
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size);
        Page<Compilation> compilationsPage = compilationRepository.findAllByPinned(pinned, pageable);
        return compilationsPage.getContent().stream()
                .map(it -> compilationMapper.toCompilationDto(it, it.getEvents().stream().map(eventMapper::eventToEventShortDto).toList()))
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getById(Long id) {
        Compilation compilation = compilationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Подборка id= " + id + " не найдена"));
        List<EventShortDto> eventsList = compilation.getEvents().stream().map(eventMapper::eventToEventShortDto).toList();
        return compilationMapper.toCompilationDto(compilation, eventsList);
    }

    @Override
    public void delete(long compilationId) {
        Compilation compilation = compilationRepository.findById(compilationId).orElseThrow(() -> new NotFoundException("Compilation " + compilationId + " не найдена."));
        compilationRepository.delete(compilation);
    }

    @Override
    public CompilationDto createCompilation(NewCompilationDto dto) {
        List<Event> events = Collections.emptyList();
        if (dto.getEvents() != null && !dto.getEvents().isEmpty()) {
            events = eventRepository.findAllById(dto.getEvents());
        }
        Compilation savedCompilation = compilationRepository.save(compilationMapper.toCompilation(dto, events));
        List<EventShortDto> eventsList = savedCompilation.getEvents().stream().map(eventMapper::eventToEventShortDto).toList();
        return compilationMapper.toCompilationDto(savedCompilation, eventsList);
    }

    @Override
    public CompilationDto updateCompilation(Long id, UpdateCompilationRequestDto dto) {
        Compilation compilation = compilationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Подборка id= " + id + " не найдена"));
        if (dto.getPinned() != null) {
            compilation.setPinned(dto.getPinned());
        }
        if (dto.getTitle() != null) {
            compilation.setTitle(dto.getTitle());
        }

        if (dto.getEvents() != null) {
            List<Event> events = Collections.emptyList();
            if (!dto.getEvents().isEmpty()) {
                events = eventRepository.findAllById(dto.getEvents());
            }
            compilation.setEvents(events);
        }

        Compilation updatedCompilation = compilationRepository.save(compilation);
        List<EventShortDto> updatedEventsList = updatedCompilation.getEvents().stream().map(eventMapper::eventToEventShortDto).toList();
        return compilationMapper.toCompilationDto(updatedCompilation, updatedEventsList);
    }
}
