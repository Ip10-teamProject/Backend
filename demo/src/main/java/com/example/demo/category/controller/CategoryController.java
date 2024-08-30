package com.example.demo.category.controller;

import com.example.demo.category.dto.CategoryRequestDto;
import com.example.demo.category.dto.CategoryResponseDto;
import com.example.demo.category.dto.updateCategoryRequestDto;
import com.example.demo.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor

public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasAnyRole('MASTER')")
    @PostMapping("")
    public ResponseEntity<List<CategoryResponseDto>> addCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        return ResponseEntity.ok()
                .body(categoryService.addCategory(categoryRequestDto));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("")
    public ResponseEntity<List<CategoryResponseDto>> getCategorys() {
        return ResponseEntity.ok()
                .body(categoryService.getCategorys());
    }

    @PreAuthorize("hasAnyRole('MASTER')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok()
                .body(categoryId+"카테고리"+" "+"삭제완료");
    }
    @PreAuthorize("hasAnyRole('MASTER')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable UUID categoryId,@RequestBody updateCategoryRequestDto updateCategoryRequestDto) {
        return ResponseEntity.ok()
                .body(categoryService.updateCategory(categoryId,updateCategoryRequestDto));
    }
}
