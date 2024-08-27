package com.example.demo.category.service;

import com.example.demo.category.dto.CategoryRequestDto;
import com.example.demo.category.dto.CategoryResponseDto;
import com.example.demo.category.dto.updateCategoryRequestDto;
import com.example.demo.category.entity.Category;
import com.example.demo.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public List<CategoryResponseDto> addCategory(CategoryRequestDto categoryRequestDto) {
        List<Category> categories = new ArrayList<>();
        for (String categoryName :categoryRequestDto.getCategory()) {
            Optional<Category> bycategoryName = categoryRepository.findBycategoryName(categoryName);
            if(!bycategoryName.isPresent()){
                categories.add(Category.createCategory(categoryName));
            }
        }
        categoryRepository.saveAll(categories);
        List<CategoryResponseDto> categoryResponseDtos = new ArrayList<>();
        for (Category category : categories) {
            categoryResponseDtos.add(new CategoryResponseDto(category.getCategory_id(),category.getCategoryName()));
        }
        return categoryResponseDtos;
    }

    public List<CategoryResponseDto>  getCategorys() {
        return categoryRepository.getCategorys();
    }

    public void deleteCategory(UUID categoryId) {
        Category category =categoryRepository.findById(categoryId).orElseThrow(() ->
                new NullPointerException("해당카테고리없음")
        );
        categoryRepository.delete(category);
    }
    @Transactional
    public CategoryResponseDto updateCategory(UUID categoryId, updateCategoryRequestDto updateCategoryRequestDto) {
        Category category =categoryRepository.findById(categoryId).orElseThrow(() ->
                new NullPointerException("해당카테고리없음")
        );
        category.updateCategory(updateCategoryRequestDto.getCategory());
        categoryRepository.save(category);
        return new CategoryResponseDto(category.getCategory_id(),category.getCategoryName());
    }
}
