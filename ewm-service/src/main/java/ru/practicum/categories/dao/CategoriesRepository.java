package ru.practicum.categories.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.categories.model.CategoryDto;

public interface CategoriesRepository extends JpaRepository<CategoryDto, Integer> {

}


