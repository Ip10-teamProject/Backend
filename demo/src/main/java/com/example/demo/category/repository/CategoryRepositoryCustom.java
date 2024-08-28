package com.example.demo.category.repository;

import com.example.demo.category.dto.CategoryResponseDto;
import com.example.demo.store.entity.StoreMapping;

import java.util.List;
import java.util.UUID;

public interface CategoryRepositoryCustom {
    List<CategoryResponseDto> getCategorys();

    List<StoreMapping> getReferenceCategory(UUID categoryId);
}
