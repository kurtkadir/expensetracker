package com.kadir.expensetracker.service;

import com.kadir.expensetracker.model.Category;
import com.kadir.expensetracker.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void save(Category category) {
        if (!categoryRepository.existsByName(category.getName())) {
            categoryRepository.save(category);
        }
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }
    public boolean existsByNameIgnoreCase(String name) {
        return categoryRepository.existsByNameIgnoreCase(name);
    }


}
