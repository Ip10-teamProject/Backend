package com.example.demo.category.entity;

import com.example.demo.store.entity.StoreMapping;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "p_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID category_id;

    @Column(name = "categoryname")
    private String categoryName;

    @Builder.Default
    @OneToMany(mappedBy = "category")
    private List<StoreMapping> storeMappings = new ArrayList<>();
    public static Category createCategory(String categoryName) {
        return Category.builder()
                .categoryName(categoryName)
                .build();
    }
    public void updateCategory(String updateCategoryName){
        this.categoryName = updateCategoryName;
    }
}
