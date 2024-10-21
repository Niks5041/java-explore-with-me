package ru.practicum.compilations.adminGroup.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.service.CompilationsService;


@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Slf4j
public class AdminCompilationsController {

    private final CompilationsService compilationsService;

    @PostMapping
    public CompilationDto saveCompilation(@RequestBody NewCompilationDto newCompilationDto) {
        log.info("Пришел POST/admin/compilations запрос с телом: " + newCompilationDto);
        CompilationDto compilationsResponseDto = compilationsService.saveCompilation(newCompilationDto);
        log.info("Отправлен POST/admin/compilations ответ c телом: " + compilationsResponseDto);
        return compilationsResponseDto;
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilationById(@PathVariable Integer compId) {
        log.info("Пришел DELETE/admin/compilations/{} запрос", compId);
        compilationsService.deleteCompilationById(compId);
        log.info("Отправлен DELETE/admin/compilations/id ответ");
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilationById(@PathVariable Integer compId,
                                                            @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Пришел PATCH/admin/compilations/{} запрос", compId);
        CompilationDto compilationsResponseDto = compilationsService.updateCompilationById(compId, newCompilationDto);
        log.info("Отправлен PATCH/admin/compilations/id ответ c телом: " + compilationsResponseDto);
        return compilationsResponseDto;
    }
}
