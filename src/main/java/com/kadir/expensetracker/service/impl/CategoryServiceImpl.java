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
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return categoryRepository.existsByNameIgnoreCase(name.trim());
    }

    @Override
    public Category findOrCreateByName(String rawName) {
        if (rawName == null || rawName.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        
        String cleaned = normalize(rawName);
        return categoryRepository.findByNameIgnoreCase(cleaned)
                .orElseGet(() -> categoryRepository.save(new Category(cleaned)));
    }

    @Override
    public Category createCategory(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        
        String normalizedName = normalize(categoryName);
        
        // Check if category already exists
        if (existsByNameIgnoreCase(normalizedName)) {
            throw new IllegalArgumentException("Category '" + normalizedName + "' already exists");
        }
        
        Category newCategory = new Category(normalizedName);
        return categoryRepository.save(newCategory);
    }

    /**
     * Normalizes category name by trimming whitespace, replacing multiple spaces with single space,
     * and capitalizing the first letter while making the rest lowercase.
     * 
     * @param rawName the raw category name to normalize
     * @return normalized category name
     */
    private String normalize(String rawName) {
        if (rawName == null) {
            throw new IllegalArgumentException("Category name cannot be null");
        }
        
        String cleaned = rawName.trim().replaceAll("\\s+", " ");
        if (cleaned.isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty after trimming");
        }
        
        return cleaned.substring(0, 1).toUpperCase() + cleaned.substring(1).toLowerCase();
    }
}
