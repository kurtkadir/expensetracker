package com.kadir.expensetracker.repository;

import com.kadir.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    // Özel sorgular istersen buraya eklenebilir
}
