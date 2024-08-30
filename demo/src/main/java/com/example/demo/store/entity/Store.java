package com.example.demo.store.entity;

import com.example.demo.global.TimeStamped;
import com.example.demo.location.entity.Location;
import com.example.demo.menu.entity.Menu;
import com.example.demo.store.users.domain.User;
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
@Table(name = "p_store")
public class Store extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "store_id")
    private UUID storeId;
    @Column(name = "storename")
    private String storeName;
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "store")
    private List<StoreMapping> storeMappings = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Menu> menus = new ArrayList<>();

    public static Store createStore(String storeName, String description, Location location , User user) {
        return Store.builder()
                .storeName(storeName)
                .description(description)
                .location(location)
                .user(user)
                .build();
    }

    public void updateStore(String storeName, String description ){
        this.storeName = storeName;
        this.description = description;
    }
    public void deleteStore(){
        super.setDeleted(true);
    }
    public void deleteLocation(){
        this.location = null;
    }
}
