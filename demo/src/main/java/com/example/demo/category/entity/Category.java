package com.example.demo.category.entity;

import com.example.demo.global.TimeStamped;
import com.example.demo.store.entity.StoreMapping;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@DynamicInsert
@Table(name = "p_category")
public class Category extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id")
    private UUID categoryId;

    @Column(name = "categoryname")
    private String categoryName;

    @ColumnDefault("false")
    @Column(name  = "isdeleted")
    private boolean isDeleted;

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
