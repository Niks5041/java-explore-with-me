package ru.practicum.compilations.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.service.CompilationsService;

import java.util.List;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
@Slf4j
public class CompilationsController {

    private final CompilationsService compilationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CompilationDto> getAll(@RequestParam(value = "pinned", required = false, defaultValue = "false") Boolean pinned,
                                       @RequestParam(value = "from", defaultValue = "0") int from,
                                       @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Получен GET /compilations запрос с параметрами: pinned={}, from={}, size={}", pinned, from, size);
        List<CompilationDto> compilations = compilationService.getAll(pinned, from, size);
        log.info("Отправлен ответ GET /compilations с количеством событий: {}", compilations.size());
        return compilations;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto getById(@PathVariable("id") long id) {
        log.info("Получен GET /compilations/{} запрос", id);
        CompilationDto compilation = compilationService.getById(id);
        log.info("Отправлен ответ GET /compilations/{}: {}", id, compilation);
        return compilation;
    }
}
