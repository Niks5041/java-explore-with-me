package ru.practicum.categories.model;

import lombok.experimental.UtilityClass;


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