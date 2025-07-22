package com.kadir.expensetracker.service;

import com.kadir.expensetracker.model.Expense;

import java.util.List;

public interface ExpenseService {
    List<Expense> findAll();

    Expense findById(Long id);

    Expense save(Expense expense);

    void delete(Long id);
}
