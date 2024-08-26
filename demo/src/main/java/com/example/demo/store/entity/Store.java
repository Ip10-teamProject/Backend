package com.example.demo.store.entity;

import com.example.demo.location.entity.Location;
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
@Table(name = "p_store")
public class Store {
    @Id
    @UUID
    private Long category_id;
    @Column(name = "storename")
    private String storeName;
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

    @OneToMany(mappedBy = "store")
    private List<StoreMapping> storeMappings = new ArrayList<>();
//    @OneToMany(mappedBy = "menu")
//    private List<Menu> menus = new ArrayList<>();
    public static Store createStore(String storeName, String description, Location location) {
        return Store.builder()
                .storeName(storeName)
                .description(description)
                .location(location)
                .build();
    }
}
