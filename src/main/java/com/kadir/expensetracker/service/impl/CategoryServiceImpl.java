package com.kadir.expensetracker.service.impl;

import com.kadir.expensetracker.model.Category;
import com.kadir.expensetracker.repository.CategoryRepository;
import com.kadir.expensetracker.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return categoryRepository.existsByNameIgnoreCase(name.trim());
    }

    @Override
    public Category findOrCreateByName(String rawName) {
        String cleaned = normalize(rawName);
        return categoryRepository.findByNameIgnoreCase(cleaned)
                .orElseGet(() -> categoryRepository.save(new Category(cleaned)));
    }

    private String normalize(String rawName) {
        String cleaned = rawName.trim().replaceAll("\\s+", " ");
        return cleaned.substring(0, 1).toUpperCase() + cleaned.substring(1).toLowerCase();
    }
}
