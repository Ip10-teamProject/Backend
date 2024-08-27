package com.example.demo.menu.dto;

import com.example.demo.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuCreateResponseDto {
    private UUID id;
    private String name;
    private Integer price;
    private String description;
    private UUID storeId;
    private LocalDateTime createdAt;

    public static MenuCreateResponseDto fromEntity(Menu menu) {
        return MenuCreateResponseDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .description(menu.getDescription())
//                .storeId(menu.getStore().getStoreId())
                .createdAt(menu.getCreatedAt())
                .build();
    }
}