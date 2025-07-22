package com.kadir.expensetracker.service;

import com.kadir.expensetracker.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    Category save(Category category);

    void delete(Long id);

    boolean existsByNameIgnoreCase(String name);

    Category findOrCreateByName(String name);
}
