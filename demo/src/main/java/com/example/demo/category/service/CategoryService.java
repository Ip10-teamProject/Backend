package com.example.demo.category.service;

import com.example.demo.category.dto.CategoryRequestDto;
import com.example.demo.category.dto.CategoryResponseDto;
import com.example.demo.category.dto.updateCategoryRequestDto;
import com.example.demo.category.entity.Category;
import com.example.demo.category.repository.CategoryRepository;
import com.example.demo.store.entity.StoreMapping;
import com.example.demo.store.repository.StoreMappingRepository;
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
    private final StoreMappingRepository storeMappingRepository;
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
            categoryResponseDtos.add(new CategoryResponseDto(category.getCategoryId(),category.getCategoryName()));
        }
        return categoryResponseDtos;
    }

    public List<CategoryResponseDto>  getCategorys() {
        return categoryRepository.getCategorys();
    }

    @Transactional
    public void deleteCategory(UUID categoryId) {
        Category category =categoryRepository.findById(categoryId)
                .filter(o -> !o.isDeleted())
                .orElseThrow(() ->
                new NullPointerException("해당카테고리없음")
        );
        category.deleteCategory();
        categoryRepository.save(category);
        List<StoreMapping>storeMappings = categoryRepository.getReferenceCategory(category.getCategoryId());
        for (StoreMapping storeMapping : storeMappings) {
            storeMapping.categoryClear();
        }
        storeMappingRepository.saveAll(storeMappings);
    }
    @Transactional
    public CategoryResponseDto updateCategory(UUID categoryId, updateCategoryRequestDto updateCategoryRequestDto) {
        Category category =categoryRepository.findById(categoryId)
                .filter(o -> !o.isDeleted())
                .orElseThrow(() ->
                new NullPointerException("해당카테고리없음")
        );
        category.updateCategory(updateCategoryRequestDto.getCategory());
        categoryRepository.save(category);
        return new CategoryResponseDto(category.getCategoryId(),category.getCategoryName());
    }
}
