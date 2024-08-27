package com.example.demo.store.dto;

import java.util.List;
import java.util.UUID;

public class StoreDto {
    private UUID storeId;
    private List<String> categories;

    public StoreDto(UUID storeId, List<String> categories) {
        this.storeId = storeId;
        this.categories = categories;
    }
}
