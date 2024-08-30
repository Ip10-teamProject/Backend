package com.example.demo.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMenuUpdateRequestDto {
    private UUID menuId;
    private Integer amount;
}
