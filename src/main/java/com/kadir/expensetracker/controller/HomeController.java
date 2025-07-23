package com.kadir.expensetracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling home page and navigation.
 * Provides basic routing for the application's main pages.
 */
@Controller
public class HomeController {

    /**
     * Display the home page
     */
    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }
}
