package com.example.demo.store.repository;

import com.example.demo.store.dto.StoreResponseDto;
import com.querydsl.core.Tuple;

import java.util.List;
import java.util.UUID;

public interface StoreRepositoryCustom {
    StoreResponseDto getStore(UUID storeId);

    List<StoreResponseDto> getStores();
}
