package com.kadir.expensetracker.controller;

import com.kadir.expensetracker.model.Expense;
import com.kadir.expensetracker.service.ExpenseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // Listeleme
    @GetMapping
    public String listExpenses(Model model) {
        model.addAttribute("expenses", expenseService.getAllExpenses());
        return "expenses"; // templates/expenses.html
    }

    // Yeni harcama formu
    @GetMapping("/new")
    public String showExpenseForm(Model model) {
        model.addAttribute("expense", new Expense());
        return "expense_form"; // templates/expense_form.html
    }

    // Kaydet
    @PostMapping("/save")
    public String saveExpense(@ModelAttribute Expense expense) {
        expenseService.saveExpense(expense);
        return "redirect:/expenses";
    }

    // Sil
    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return "redirect:/expenses";
    }
}
