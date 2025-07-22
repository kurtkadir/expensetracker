package com.kadir.expensetracker.service.impl;

import com.kadir.expensetracker.model.Expense;
import com.kadir.expensetracker.repository.ExpenseRepository;
import com.kadir.expensetracker.service.ExpenseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
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
    public void delete(Long id) {
        expenseRepository.deleteById(id);
    }
}
