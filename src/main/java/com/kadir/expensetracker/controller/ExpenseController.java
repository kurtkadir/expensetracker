package com.kadir.expensetracker.controller;

import com.kadir.expensetracker.model.Expense;
import com.kadir.expensetracker.service.ExpenseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;


    }


    // Listing
    @GetMapping
    public String listExpenses(Model model) {
        model.addAttribute("expenses", expenseService.getAllExpenses());
        return "expenses"; // templates/expenses.html
    }

    //new
    @GetMapping("/new")
    public String showExpenseForm(Model model) {
        prepareExpenseForm(model, new Expense());
        return "expense_form";
    }




    // Save
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
    //Update

    @GetMapping("/edit/{id}")
    public String editExpense(@PathVariable Long id, Model model) {
        Expense expense = expenseService.getExpenseById(id)
                .orElseThrow(() -> new IllegalArgumentException("Geçersiz ID: " + id));
        prepareExpenseForm(model, expense);
        return "expense_form";
    }




    // Categories

    private void prepareExpenseForm(Model model, Expense expense) {
        model.addAttribute("expense", expense);
        model.addAttribute("categories", List.of("Gıda", "Ulaşım", "Fatura", "Eğlence", "Sağlık", "Diğer"));
    }



}
