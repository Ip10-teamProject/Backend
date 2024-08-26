package com.example.demo.location.entity;

import com.example.demo.store.entity.Store;
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
@Table(name = "p_location")

public class Location {
    @Id
    @UUID
    private Long location_id;
    @Column(name = "address")
    private String address;
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
