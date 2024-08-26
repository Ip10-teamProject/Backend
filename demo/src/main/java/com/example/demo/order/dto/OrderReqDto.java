package com.example.demo.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderReqDto {
    private Long userId;
    private UUID paymentId;
    private UUID storeId;
    private String memo;
}
