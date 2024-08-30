package com.example.demo.order.dto;

import com.example.demo.order.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusReqDto {
    private OrderStatus status;
}
