package ru.practicum.categories.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.practicum.categories.dao.CategoryRepository;
import ru.practicum.categories.model.Category;
import ru.practicum.categories.model.CategoryDto;
import ru.practicum.categories.model.CategoryMapper;
import ru.practicum.categories.model.NewCategoryDto;
import ru.practicum.exceptions.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.categories.model.CategoryMapper.dtoToCategory;
import static ru.practicum.categories.model.CategoryMapper.toCategoryDto;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(@Validated NewCategoryDto dto) {
        return toCategoryDto(categoryRepository.save(dtoToCategory(dto)));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto dto) {

        Category category = categoryRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Категория id= " + dto.getId() + " не найдена"));
        category.setName(dto.getName());

        return toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public void delete(long categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Категория id= " + categoryId + " не найдена"));
        categoryRepository.deleteById(categoryId);
    }


    @Override
    public List<CategoryDto> getAll(int from, int size) {
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size);
        Page<Category> categoriesPage = categoryRepository.findAll(pageable);

        return categoriesPage.getContent().stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Категория id= " + categoryId + " не найдена"));

        return toCategoryDto(category);
    }
}
