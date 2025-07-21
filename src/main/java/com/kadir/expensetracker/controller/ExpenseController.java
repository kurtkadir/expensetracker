package com.kadir.expensetracker.controller;

import com.kadir.expensetracker.model.Expense;
import com.kadir.expensetracker.service.CategoryService;
import com.kadir.expensetracker.service.ExpenseService;
import com.kadir.expensetracker.util.CategoryUtil;
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

    // Harcamaları listele
    @GetMapping
    public String listExpenses(Model model) {
        model.addAttribute("expenses", expenseService.getAllExpenses());
        return "expenses"; // templates/expenses.html
    }

    // Yeni harcama formu
    @GetMapping("/new")
    public String showExpenseForm(Model model) {
        model.addAttribute("expense", new Expense());
        model.addAttribute("categories", categoryService.findAll()); // kategori listesini ver
        return "expense_form"; // templates/expense_form.html
    }

    // Harcama kaydet
    @PostMapping("/save")
    public String saveExpense(@ModelAttribute Expense expense,
                              @RequestParam("category") String categoryRaw) {

        // Kategoriyi normalize et
        String normalizedCategory = CategoryUtil.normalize(categoryRaw);

        // Kategorinin önceden var olduğunu varsayıyoruz, yoksa hata olabilir (isteğe bağlı kontrol)
        if (!categoryService.existsByNameIgnoreCase(normalizedCategory)) {
            // İstersen burada hata fırlatabilir ya da kullanıcıyı yönlendirebilirsin
            // throw new IllegalArgumentException("Kategori mevcut değil: " + normalizedCategory);
        }

        expense.setCategory(normalizedCategory);

        expenseService.saveExpense(expense);
        return "redirect:/expenses";
    }

    // Harcama sil
    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return "redirect:/expenses";
    }

    // Harcama düzenle
    @GetMapping("/edit/{id}")
    public String editExpense(@PathVariable Long id, Model model) {
        Expense expense = expenseService.getExpenseById(id)
                .orElseThrow(() -> new IllegalArgumentException("Geçersiz ID: " + id));
        model.addAttribute("expense", expense);
        model.addAttribute("categories", categoryService.findAll());
        return "expense_form";
    }
}
