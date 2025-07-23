package com.kadir.expensetracker.service;

import com.kadir.expensetracker.model.Expense;

import java.util.List;

/**
 * Service interface for expense-related operations.
 * Defines methods for managing expense entities and business logic.
 */
public interface ExpenseService {
    List<Expense> findAll();

    Expense findById(Long id);

    Expense save(Expense expense);

    void delete(Long id);

    Expense saveWithCategory(Expense expense, String rawCategoryName);
}
