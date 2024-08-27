package com.example.demo.category.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CategoryResponseDto {
    UUID categoryId;
    String categoryName;
    public CategoryResponseDto(UUID categoryId, String categoryName){
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
