package com.kadir.expensetracker.controller;

import com.kadir.expensetracker.model.Category;
import com.kadir.expensetracker.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * List all categories
     */
    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("category", new Category());
        return "categories";
    }

    /**
     * Show new category form
     */
    @GetMapping("/new")
    public String showCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "category_form";
    }

    /**
     * Save category with validation
     */
    @PostMapping("/save")
    public String saveCategory(@ModelAttribute @Valid Category category, 
                              BindingResult bindingResult, 
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "categories";
        }
        
        try {
            // Use the new createCategory method for consistent normalization
            categoryService.createCategory(category.getName());
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("categories", categoryService.findAll());
            return "categories";
        }
        
        return "redirect:/categories";
    }

    /**
     * Delete category by ID
     */
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/categories";
    }
}
