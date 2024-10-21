package ru.practicum.categories.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.categories.dao.CategoriesRepository;
import ru.practicum.categories.model.CategoryDto;
import ru.practicum.exceptions.NotFoundException;

import java.util.Collection;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryServiceImpl implements CategoriesService {

    CategoriesRepository categoriesRepository;

    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        log.info("Пришел запрос на добавление новой категории в сервис Categories с телом: " + categoryDto);
        return categoriesRepository.save(categoryDto);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer catId) {
        log.info("Пришел запрос на обновление категории в сервис Categories с ID = {} и телом: " + categoryDto, catId);

        return categoriesRepository.findById(catId)
                .map(existCategory -> {
                        existCategory.setName(categoryDto.getName());
                return categoriesRepository.save(existCategory);
                })
                .orElseThrow(() ->new NotFoundException("Данная категоря не найдена"));
    }

    @Override
    public void deleteCategory(Integer catId) {
        log.info("Пришел запрос на удаление категории в сервис Categories с ID = {} ", catId);
        categoriesRepository.deleteById(catId);
    }

    @Override
    public Collection<CategoryDto> getCategories(Integer from, Integer size) {
        log.info("Пришел запрос в сервис Categories на получение категорий по параметрам from = {}, size = {} ",
                from, size);
        return categoriesRepository.findAll().stream().limit(size).toList(); // дописать запрос в бд
    }

    @Override
    public CategoryDto getInfoOfCategory(Integer catId) {
        log.info("Пришел запрос в сервис Categories на получение категории по ID: " + catId);
        return categoriesRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Категория не найдена"));
    }
}
