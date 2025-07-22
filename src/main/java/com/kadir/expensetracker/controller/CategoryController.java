package com.kadir.expensetracker.controller;

import com.kadir.expensetracker.model.Category;
import com.kadir.expensetracker.service.CategoryService;
import com.kadir.expensetracker.util.CategoryUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Kategorileri listele
    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("category", new Category());
        return "categories"; // templates/categories.html
    }

    // Yeni kategori formu
    @GetMapping("/new")
    public String showCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        System.out.println();
        return "category_form"; // yeni kategori formu şablonu
    }

    // Kategori kaydet
    @PostMapping("/save")
    public String saveCategory(@ModelAttribute Category category) {
        if (!categoryService.existsByNameIgnoreCase(category.getName())) {
            String normalizedCategory = CategoryUtil.normalize(String.valueOf(category));
            System.out.println(category);
            categoryService.save(category);
        }
        return "redirect:/categories";
    }

    // Kategori sil
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/categories";
    }
}
