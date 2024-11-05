package ru.practicum.categories.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.categories.model.CategoryDto;
import ru.practicum.categories.service.CategoryService;

import java.util.List;


@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getAll(@RequestParam(value = "from", defaultValue = "0") int from,
                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Получен GET /categories запрос с параметрами: from = {}, size = {}", from, size);
        List<CategoryDto> categories = categoryService.getAll(from, size);
        log.info("Отправлен ответ с {} категориями", categories.size());
        return categories;
    }

    @GetMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getById(@PathVariable("catId") long catId) {
        log.info("Получен GET /categories/{} запрос для получения категории по ID", catId);
        CategoryDto categoryDto = categoryService.getById(catId);
        log.info("Отправлен ответ для категории с ID: {}, данные: {}", catId, categoryDto);
        return categoryDto;
    }

}
