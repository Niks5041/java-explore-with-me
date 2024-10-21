package ru.practicum.categories.adminGroup.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.categories.model.CategoryDto;
import ru.practicum.categories.model.NewCategoryDto;
import ru.practicum.categories.service.CategoryService;


@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Slf4j
public class AdminCategoriesController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@Valid @RequestBody NewCategoryDto dto) {
        log.info("Пришел POST/admin/categories запрос с телом: " + dto);
        CategoryDto categoryDto = categoryService.createCategory(dto);
        log.info("Отправлен POST/admin/categories ответ c телом: " + dto);
        return categoryDto;
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable long catId) {
        log.info("Пришел DELETE/admin/categories запрос с ID: {}", catId);
        categoryService.delete(catId);
    }

    @PatchMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(@PathVariable long catId, @Valid @RequestBody CategoryDto categoryDto) {
        categoryDto.setId(catId);
        log.info("Пришел PATCH/admin/categories/{} запрос с телом: " + categoryDto, catId);
        CategoryDto updateCategory = categoryService.updateCategory(categoryDto);
        log.info("Отправлен PATCH/admin/categories ответ c телом: " + updateCategory);
        return updateCategory;
    }
}
