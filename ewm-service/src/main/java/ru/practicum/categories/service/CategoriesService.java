package ru.practicum.categories.service;

import ru.practicum.categories.model.CategoryDto;

import java.util.Collection;

public interface CategoriesService {

    CategoryDto saveCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, Integer catId);

    void deleteCategory(Integer catId);

    Collection<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getInfoOfCategory(Integer catId);
}


