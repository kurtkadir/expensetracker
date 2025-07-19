package com.kadir.expensetracker.repository;
import com.kadir.expensetracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name); // Duplicate engelleme için
    boolean existsByNameIgnoreCase(String name);
}
