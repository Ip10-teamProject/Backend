package com.example.demo.category.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponseDto {
    Long categoryId;
    String categoryName;
    public CategoryResponseDto(Long categoryId, String categoryName){
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
