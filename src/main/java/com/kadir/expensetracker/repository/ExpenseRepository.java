package com.kadir.expensetracker.repository;

import com.kadir.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
    /**
     * Find expenses by category name
     */
    List<Expense> findByCategoryIgnoreCase(String category);
    
    /**
     * Find expenses between two dates
     */
    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    /**
     * Find expenses by category and date range
     */
    List<Expense> findByCategoryIgnoreCaseAndDateBetween(String category, LocalDate startDate, LocalDate endDate);
    
    /**
     * Find expenses with amount greater than specified value
     */
    List<Expense> findByAmountGreaterThan(java.math.BigDecimal amount);
    
    /**
     * Custom query to find expenses by title containing keyword
     */
    @Query("SELECT e FROM Expense e WHERE LOWER(e.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Expense> findByTitleContainingIgnoreCase(@Param("keyword") String keyword);
}
