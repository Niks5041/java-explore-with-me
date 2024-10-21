package ru.practicum.categories.service;

import ru.practicum.categories.model.CategoryDto;
import ru.practicum.categories.model.NewCategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAll(int from, int size);

    CategoryDto getById(Long categoryId);

    void delete(long categoryId);

    CategoryDto createCategory(NewCategoryDto dto);

    CategoryDto updateCategory(CategoryDto dto);
}