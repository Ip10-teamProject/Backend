package com.example.demo.store.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class StoreCreateRequestDto {
    UUID location_id;
    List<UUID>category_id;
    String storeName;
    String description;
}
