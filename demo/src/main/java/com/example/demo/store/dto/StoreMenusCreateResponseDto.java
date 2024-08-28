package com.example.demo.store.dto;

import com.example.demo.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreMenusCreateResponseDto {
    private String name;
    private Integer price;
    private String description;

    public StoreMenusCreateResponseDto(Menu menu) {
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.description = menu.getDescription();
    }
}
