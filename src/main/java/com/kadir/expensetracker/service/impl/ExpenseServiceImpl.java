package com.kadir.expensetracker.service.impl;

import com.kadir.expensetracker.model.Category;
import com.kadir.expensetracker.model.Expense;
import com.kadir.expensetracker.repository.ExpenseRepository;
import com.kadir.expensetracker.service.CategoryService;
import com.kadir.expensetracker.service.ExpenseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of ExpenseService interface.
 * Provides business logic for expense-related operations including CRUD operations.
 */
@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryService categoryService;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, CategoryService categoryService) {
        this.expenseRepository = expenseRepository;
        this.categoryService = categoryService;
    }

    @Override
    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }

    @Override
    public Expense findById(Long id) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        return optionalExpense.orElse(null);
    }

    @Override
    public Expense save(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public Expense saveWithCategory(Expense expense, String rawCategoryName) {
        if (rawCategoryName != null && !rawCategoryName.trim().isEmpty()) {
            // Use findOrCreateByName for expense creation (allows existing categories)
            Category category = categoryService.findOrCreateByName(rawCategoryName);
            expense.setCategory(category.getName());
        }
        return expenseRepository.save(expense);
    }

    @Override
    public void delete(Long id) {
        expenseRepository.deleteById(id);
    }
}
