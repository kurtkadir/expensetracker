package com.kadir.expensetracker.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for centralized error management
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle IllegalArgumentException and return user-friendly error message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error";
    }

    /**
     * Handle general exceptions and return generic error message
     */
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception e, Model model) {
        model.addAttribute("errorMessage", "An unexpected error occurred. Please try again.");
        return "error";
    }
} 