package ru.practicum.compilations.publicGroup.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.service.CompilationsService;

import java.util.Collection;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
@Slf4j
public class CompilationsController {

    private final CompilationsService compilationsService;

    @GetMapping
    public  Collection<CompilationDto> getCompilations(@RequestParam Boolean pinned,
                                               @RequestParam(defaultValue = "0") Integer from,
                                               @RequestParam(defaultValue = "10") Integer size) {
        log.info("Пришел GET /compilations запрос с параметрами pinned = {}, from = {}, size = {}",
                pinned, from, size);
        Collection<CompilationDto> compilationsResponseDto = compilationsService.getCompilations(pinned, from, size);
        log.info("Отправлен GET /compilations ответ c телом: " + compilationsResponseDto);
        return compilationsResponseDto;
    }

    @GetMapping("/{compId}")
    public Collection<CompilationDto> getCompilationsById(@PathVariable Integer compId) {
        log.info("Пришел GET /compilations/{} запрос", compId);
        Collection<CompilationDto> compilationsResponseDto = compilationsService.getCompilationsById(compId);
        log.info("Отправлен GET /compilations/id ответ c телом: " + compilationsResponseDto);
        return compilationsResponseDto;
    }
}
