package com.example.demo.store.entity;

import com.example.demo.category.entity.Category;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "p_storeMapping")
public class StoreMapping {
    @Id
    @UUID
    private Long storeMapping_id;
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
