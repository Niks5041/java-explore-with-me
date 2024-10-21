package ru.practicum.categories.publicGroup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.categories.model.CategoryDto;
import ru.practicum.categories.service.CategoriesService;

import java.util.Collection;


@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoriesController {

    private final CategoriesService categoriesService;

    @GetMapping
    public Collection<CategoryDto> getCategories(@RequestParam(defaultValue = "0") Integer from,
                                                 @RequestParam(defaultValue = "10") Integer size) {
        log.info("Пришел GET/categories запрос с параметрами from = {}, size = {} ", from, size);
        Collection<CategoryDto> categoryDtos = categoriesService.getCategories(from, size);
        log.info("Отправлен GET/categories ответ c телом: " + categoryDtos);
        return categoryDtos;
    }

    @GetMapping("/{catId}")
    public CategoryDto getInfoOfCategory(@PathVariable Integer catId) {
        log.info("Пришел GET/categories/{} запрос с ID: "+ catId, catId);
        CategoryDto categoryDto = categoriesService.getInfoOfCategory(catId);
        log.info("Отправлен GET/categories/{} ответ c телом: " + categoryDto, catId);
        return categoryDto;
    }
}
