package com.kadir.expensetracker.controller;

import com.kadir.expensetracker.model.Expense;
import com.kadir.expensetracker.service.CategoryService;
import com.kadir.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing expense-related operations.
 * Handles CRUD operations for expenses and provides form handling.
 */
@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;

    public ExpenseController(ExpenseService expenseService, CategoryService categoryService) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
    }

    /**
     * Display list of all expenses
     */
    @GetMapping
    public String listExpenses(Model model) {
        model.addAttribute("expenses", expenseService.findAll());
        return "expenses";
    }

    /**
     * Show form for creating new expense
     */
    @GetMapping("/new")
    public String showExpenseForm(Model model) {
        prepareExpenseForm(model, new Expense());
        return "expense_form";
    }

    /**
     * Save expense with validation and category handling
     */
    @PostMapping("/save")
    public String saveExpense(@ModelAttribute("expense") @Valid Expense expense,
                              BindingResult bindingResult,
                              @RequestParam(name = "category", required = false) String rawCategoryName,
                              Model model) {
        if (bindingResult.hasErrors()) {
            prepareExpenseForm(model, expense);
            return "expense_form";
        }

        expenseService.saveWithCategory(expense, rawCategoryName);
        
        return "redirect:/expenses";
    }

    /**
     * Show form for editing existing expense
     */
    @GetMapping("/edit/{id}")
    public String editExpense(@PathVariable Long id, Model model) {
        Expense expense = expenseService.findById(id);
        prepareExpenseForm(model, expense);
        return "expense_form";
    }

    /**
     * Delete expense by ID
     */
    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseService.delete(id);
        return "redirect:/expenses";
    }

    /**
     * Helper method to prepare the expense form with categories
     */
    private void prepareExpenseForm(Model model, Expense expense) {
        model.addAttribute("expense", expense);
        model.addAttribute("categories", categoryService.findAll());
    }
}
