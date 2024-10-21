package ru.practicum.compilations.publicGroup.controller;


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
        return compilationService.getAll(pinned, from, size);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto getById(@PathVariable("id") long id) {
        return compilationService.getById(id);
    }
}
