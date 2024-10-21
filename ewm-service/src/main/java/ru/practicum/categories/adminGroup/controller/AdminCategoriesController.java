package ru.practicum.categories.adminGroup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.categories.model.CategoryDto;
import ru.practicum.categories.service.CategoriesService;


@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Slf4j
public class AdminCategoriesController {

    private final CategoriesService categoriesService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto saveCategory(@RequestBody CategoryDto category) {
        log.info("Пришел POST/admin/categories запрос с телом: " + category);
        CategoryDto categoryDto = categoriesService.saveCategory(category);
        log.info("Отправлен POST/admin/categories ответ c телом: " + categoryDto);
        return categoryDto;
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@RequestBody CategoryDto category,
                                      @PathVariable Integer catId) {
        log.info("Пришел PATCH/admin/categories/{} запрос с телом: " + category, catId);
        CategoryDto categoryDto = categoriesService.updateCategory(category, catId);
        log.info("Отправлен PATCH/admin/categories ответ c телом: " + categoryDto);
        return categoryDto;
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable Integer catId) {
        log.info("Пришел DELETE/admin/categories запрос с ID: {}", catId);
        categoriesService.deleteCategory(catId);
        log.info("Отправлен ответ DELETE/admin/categories запрос с ID: {}", catId);
    }
}
