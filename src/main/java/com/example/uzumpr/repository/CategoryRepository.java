package com.example.uzumpr.repository;

import com.example.uzumpr.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByParentId(Integer id);
}
