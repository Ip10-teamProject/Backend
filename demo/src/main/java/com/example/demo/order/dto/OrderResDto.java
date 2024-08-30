package com.example.demo.order.dto;

import com.example.demo.order.entity.Order;
import com.example.demo.order.entity.OrderStatus;
import com.example.demo.order.entity.OrderType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class OrderResDto {
    private UUID id;
    private String userName;
    private String memo;
    private String address;
    private OrderStatus status;
    private OrderType type;

    public OrderResDto(Order order) {
        this.id = order.getId();
        this.userName = order.getUser().getUsername();
        this.memo = order.getMemo();
        this.address = order.getAddress();
        this.status = order.getStatus();
        this.type = order.getType();
    }
}
