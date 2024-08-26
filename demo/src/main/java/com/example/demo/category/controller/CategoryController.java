package com.example.demo.category.controller;

import com.example.demo.category.dto.CategoryRequestDto;
import com.example.demo.category.dto.CategoryResponseDto;
import com.example.demo.category.dto.updateCategoryRequestDto;
import com.example.demo.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor

public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("")
    public ResponseEntity<String> addCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        categoryService.addCategory(categoryRequestDto);
        return ResponseEntity.ok()
                .body("asd");
    }
    @GetMapping("")
    public ResponseEntity<List<CategoryResponseDto>> getCategorys() {
        return ResponseEntity.ok()
                .body(categoryService.getCategorys());
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok()
                .body("asd");
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId,@RequestBody updateCategoryRequestDto updateCategoryRequestDto) {
        categoryService.updateCategory(categoryId,updateCategoryRequestDto);
        return ResponseEntity.ok()
                .body("asd");
    }
}
