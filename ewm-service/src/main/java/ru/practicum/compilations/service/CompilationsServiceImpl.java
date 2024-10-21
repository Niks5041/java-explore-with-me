package ru.practicum.compilations.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.compilations.dao.CompilationsRepository;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.dto.mapper.CompilationMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


@Slf4j
@Service
@AllArgsConstructor
public class CompilationsServiceImpl implements CompilationsService {

    CompilationsRepository compilationsRepository;


    @Override
    public Collection<CompilationDto> getCompilations(Boolean pinned,
                                                      Integer from,
                                                      Integer size) {
        log.info("Пришел запрос на получение в сервис Compilations с параметрами pinned = {}, from = {}, size = {}",
                pinned, from, size);

        Collection<CompilationDto> compilationDtos = compilationsRepository.findAll().stream()
                .map(CompilationMapper.INSTANCE::toCompilationDto)
                .toList();
        if (compilationDtos == null) {
            return new ArrayList<>();
        }

        return compilationDtos;          // дописать запрос в бд
    }

    @Override
    public Collection<CompilationDto> getCompilationsById(Integer comId) {
        log.info("Пришел запрос на получение в сервис Compilations с id = {}", comId);

        return compilationsRepository.findAllById(Collections.singleton(comId)).stream()
                .map(CompilationMapper.INSTANCE::toCompilationDto)
                .toList();
    }

    @Override
    public CompilationDto saveCompilation(NewCompilationDto newCompilationDto) {
        log.info("Пришел запрос на сохранение в сервис Compilations с телом: {}", newCompilationDto);
        return CompilationMapper.INSTANCE.toCompilationDto(
                compilationsRepository.save(CompilationMapper.INSTANCE.toCompilation(newCompilationDto)));
    }

    @Override
    public void deleteCompilationById(Integer compId) {
        log.info("Пришел запрос на удаление в сервис Compilations с id = {}", compId);
        compilationsRepository.deleteById(compId);
    }

    @Override
    public CompilationDto updateCompilationById(Integer compId, NewCompilationDto newCompilationDto) {
        log.info("Пришел запрос на обновление в сервис Compilations с id = {} и телом" + newCompilationDto, compId);
        return CompilationMapper.INSTANCE.toCompilationDto(
                compilationsRepository.save(CompilationMapper.INSTANCE.toCompilation(newCompilationDto)));
    }
}
