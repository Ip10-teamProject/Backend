package com.example.demo.category.entity;

import com.example.demo.store.entity.StoreMapping;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "p_category")
public class Category {
    @Id
    @UUID
    private Long category_id;
    @Column(name = "categoryname")
    private String categoryName;

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
