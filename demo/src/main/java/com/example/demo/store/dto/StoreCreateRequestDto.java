package com.example.demo.store.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class StoreCreateRequestDto {
    UUID locationId;
    List<UUID>categoryId;
    String storeName;
    String description;
}
