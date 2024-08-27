package com.example.demo.location.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LocationResponseDto {
    UUID locationId;
    String address;
    public LocationResponseDto(UUID locationId, String address){
        this.locationId = locationId;
        this.address = address;
    }
}
