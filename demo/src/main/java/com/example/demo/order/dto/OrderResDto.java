package com.example.demo.order.dto;

import com.example.demo.order.entity.Order;
import com.example.demo.order.entity.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class OrderResDto {
    private UUID id;
    private Long userId;
    private UUID paymentId;
    private UUID storeId;
    private String memo;
    private OrderStatus status;

    public OrderResDto(Order order) {
        this.id = order.getId();
        this.userId = order.getUserId();
        this.paymentId = order.getPaymentId();
        this.storeId = order.getStoreId();
        this.memo = order.getMemo();
        this.status = order.getStatus();
    }
}
