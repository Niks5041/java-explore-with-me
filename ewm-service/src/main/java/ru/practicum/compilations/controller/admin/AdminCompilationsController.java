package ru.practicum.compilations.controller.admin;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.dto.UpdateCompilationRequestDto;
import ru.practicum.compilations.service.CompilationsService;


@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Slf4j
public class AdminCompilationsController {

    private final CompilationsService compilationService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(
            @Valid @RequestBody NewCompilationDto dto) {
        log.info("Пришел POST /admin/compilations запрос с телом: {}", dto);
        CompilationDto response = compilationService.createCompilation(dto);
        log.info("Отправлен POST /admin/compilations ответ: {}", response);
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable long id) {
        log.info("Пришел DELETE /admin/compilations/{} запрос", id);
        compilationService.delete(id);
        log.info("Удаление завершено для категории с ID: {}", id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto updateCompilation(@PathVariable long id, @Valid @RequestBody UpdateCompilationRequestDto compilationDto) {
        log.info("Пришел PATCH /admin/compilations/{} запрос с телом: {}", id, compilationDto);
        CompilationDto response = compilationService.updateCompilation(id, compilationDto);
        log.info("Отправлен ответ с обновленными данными: {}", response);
        return response;
    }
}
