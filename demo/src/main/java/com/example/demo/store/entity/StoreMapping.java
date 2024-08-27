package com.example.demo.store.entity;

import com.example.demo.category.entity.Category;
import com.example.demo.global.TimeStamped;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "p_storeMapping")
public class StoreMapping extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID storeMapping_id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public static StoreMapping createStoreMapping(Store store , Category category) {
        return StoreMapping.builder()
                .store(store)
                .category(category)
                .build();
    }
}
