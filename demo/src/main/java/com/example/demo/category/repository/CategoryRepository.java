package com.example.demo.category.repository;

import com.example.demo.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> , CategoryRepositoryCustom {
    Optional<Category> findBycategoryName(String categoryName);


}
