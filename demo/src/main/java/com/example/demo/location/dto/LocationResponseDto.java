package com.example.demo.location.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationResponseDto {
    Long locationId;
    String address;
    public LocationResponseDto(Long locationId, String address){
        this.locationId = locationId;
        this.address = address;
    }
}
