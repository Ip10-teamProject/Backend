package com.example.demo.location.entity;

import com.example.demo.global.TimeStamped;
import com.example.demo.store.entity.Store;
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
@Table(name = "p_location")
public class Location extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "location_id")
    private UUID locationId;

    @Column(name = "address")
    private String address;

    @ColumnDefault("false")
    @Column(name  = "isdeleted")
    private boolean isDeleted;

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
