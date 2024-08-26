package com.example.demo.store.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StoreCreateRequestDto {
    Long location_id;
    List<Long>category_id;
    String storeName;
    String description;
}
