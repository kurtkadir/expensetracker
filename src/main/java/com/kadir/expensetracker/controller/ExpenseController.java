package com.kadir.expensetracker.controller;

import com.kadir.expensetracker.model.Category;
import com.kadir.expensetracker.model.Expense;
import com.kadir.expensetracker.service.ExpenseService;
import com.kadir.expensetracker.service.CategoryService;
import com.kadir.expensetracker.controller.CategoryController.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.kadir.expensetracker.util.CategoryUtil;

import java.util.List;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;

    public ExpenseController(ExpenseService expenseService, CategoryService categoryService) {
        this.expenseService = expenseService;
        this.categoryService= categoryService;


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
    public String saveExpense(
            @ModelAttribute Expense expense,
            @RequestParam(value = "category", required = false) String categoryFromInput,
            @RequestParam(value = "category_dummy", required = false) String categoryFromDropdown
    ) {
        String finalCategoryRaw;

        if (categoryFromInput != null && !categoryFromInput.isBlank()) {
            finalCategoryRaw = categoryFromInput;
        } else if (categoryFromDropdown != null && !categoryFromDropdown.equals("__other__")) {
            finalCategoryRaw = categoryFromDropdown;
        } else {
            finalCategoryRaw = "Belirtilmemiş";
        }

// ✅ normalize et
        String finalCategory = CategoryUtil.normalize(finalCategoryRaw);

// Kullan
        expense.setCategory(finalCategory);

        if (!categoryService.existsByNameIgnoreCase(finalCategory)) {
            categoryService.save(new Category(finalCategory));
        }

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

    private void prepareExpenseForm( Model model, Expense expense) {
        model.addAttribute("expense", expense);
        model.addAttribute("categories", categoryService.findAll());
    }







}
