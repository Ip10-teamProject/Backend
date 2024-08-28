package com.example.demo.location.repository;

import com.example.demo.location.dto.LocationResponseDto;
import com.example.demo.store.entity.Store;

import java.util.List;
import java.util.UUID;

public interface LocationRepositoryCustom {
    List<LocationResponseDto> getLocations();

    List<Store> getReferenceStore(UUID locationId);
}
