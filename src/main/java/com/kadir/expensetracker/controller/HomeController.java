package com.kadir.expensetracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
