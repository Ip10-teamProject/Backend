package com.example.demo.category.service;

import com.example.demo.category.dto.CategoryRequestDto;
import com.example.demo.category.dto.CategoryResponseDto;
import com.example.demo.category.dto.updateCategoryRequestDto;
import com.example.demo.category.entity.Category;
import com.example.demo.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public void addCategory(CategoryRequestDto categoryRequestDto) {
        List<Category> categories = new ArrayList<>();
        for (String categoryName :categoryRequestDto.getCategory()) {
            if(!categoryRepository.findBycategoryName(categoryName)){
                categories.add(Category.createCategory(categoryName));
            }
        }
        categoryRepository.saveAll(categories);
    }

    public List<CategoryResponseDto>  getCategorys() {
        return categoryRepository.getCategorys();
    }

    public void deleteCategory(Long categoryId) {
        Optional<Category>category =categoryRepository.findById(categoryId);
        category.ifPresent(categoryRepository::delete);
    }

    public void updateCategory(Long categoryId, updateCategoryRequestDto updateCategoryRequestDto) {
        Optional<Category>category =categoryRepository.findById(categoryId);
        if(category.isPresent()){
            Category updateCategory=category.get();
            updateCategory.updateCategory(updateCategory.getCategoryName());
            categoryRepository.save(updateCategory);
        }else {
            //
        }
    }
}
