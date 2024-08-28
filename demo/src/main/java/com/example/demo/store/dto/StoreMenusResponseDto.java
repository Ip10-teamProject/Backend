package com.example.demo.store.dto;

import com.example.demo.menu.entity.Menu;
import com.example.demo.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreMenusResponseDto {
    private String name;
    private String description;
    private Integer price;


    public StoreMenusResponseDto(Menu storeMenu) {
        this.name = storeMenu.getName();
        this.description = storeMenu.getDescription();
        this.price = storeMenu.getPrice();
    }

}
