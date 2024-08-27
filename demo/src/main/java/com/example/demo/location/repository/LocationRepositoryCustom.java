package com.example.demo.location.repository;

import com.example.demo.location.dto.LocationResponseDto;

import java.util.List;

public interface LocationRepositoryCustom {
    List<LocationResponseDto> getLocations();
}
