package ru.practicum.categories.model.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.categories.model.Category;
import ru.practicum.categories.model.CategoryDto;
import ru.practicum.categories.model.NewCategoryDto;


@UtilityClass
public class CategoryMapper {
    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }


    public static Category dtoToCategory(NewCategoryDto newCategoryDto) {

        Category category = new Category();
        category.setName(newCategoryDto.getName());
        return category;
    }
}