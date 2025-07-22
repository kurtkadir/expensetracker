package com.kadir.expensetracker.controller;

import com.kadir.expensetracker.model.Category;
import com.kadir.expensetracker.model.Expense;
import com.kadir.expensetracker.service.CategoryService;
import com.kadir.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;

    public ExpenseController(ExpenseService expenseService, CategoryService categoryService) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listExpenses(Model model) {
        model.addAttribute("expenses", expenseService.findAll());
        return "expenses";
    }

    @GetMapping("/new")
    public String showExpenseForm(Model model) {
        prepareExpenseForm(model, new Expense());
        return "expense_form";
    }

    @PostMapping("/save")
    public String saveExpense(@ModelAttribute("expense") @Valid Expense expense,
                              @RequestParam(name = "category", required = false) String rawCategoryName) {

        if (rawCategoryName != null && !rawCategoryName.trim().isEmpty()) {
            Category category = categoryService.findOrCreateByName(rawCategoryName);
            expense.setCategory(category.getName()); // Expense içinde sadece ad string olarak tutuluyor
        }

        expenseService.save(expense);
        return "redirect:/expenses";
    }

    @GetMapping("/edit/{id}")
    public String editExpense(@PathVariable Long id, Model model) {
        Expense expense = expenseService.findById(id);
        System.out.println();
        if (expense == null) return "redirect:/expenses"; // not found
        prepareExpenseForm(model, expense);
        return "expense_form";
    }

    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseService.delete(id);
        return "redirect:/expenses";
    }

    // Yardımcı metot
    private void prepareExpenseForm(Model model, Expense expense) {
        model.addAttribute("expense", expense);
        model.addAttribute("categories", categoryService.findAll());
    }
}
