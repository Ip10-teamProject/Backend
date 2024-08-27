package com.example.demo.location.entity;

import com.example.demo.global.TimeStamped;
import com.example.demo.store.entity.Store;
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
@Table(name = "p_location")
public class Location extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID location_id;

    @Column(name = "address")
    private String address;
    @Builder.Default
    @OneToMany(mappedBy = "location")
    private List<Store> stores = new ArrayList<>();
    public static Location createLocation(String locationName) {
        return Location.builder()
                .address(locationName)
                .build();
    }

    public void updateLocation(String locationName) {
        this.address =locationName;
    }
}
