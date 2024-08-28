package com.example.demo.store.repository;

import com.example.demo.store.dto.StoreResponseDto;
import com.example.demo.store.entity.StoreMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface StoreRepositoryCustom {
    StoreResponseDto getStore(UUID storeId);

    Page<StoreResponseDto> getStores(Pageable pageable);
    List<StoreMapping> getReferenceStore(UUID storeId);

    Page<StoreResponseDto> CategoryStores(UUID categoryId, Pageable pageable);

    Page<StoreResponseDto> locationStores(UUID locationId, Pageable pageable);

    Page<StoreResponseDto> SearchStores(String name, Pageable pageable);
}
