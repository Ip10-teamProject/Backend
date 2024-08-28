package com.example.demo.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuCreateRequestDto {

    private String name;
    private Integer price;
    private String description;
    private UUID storeId;
}
