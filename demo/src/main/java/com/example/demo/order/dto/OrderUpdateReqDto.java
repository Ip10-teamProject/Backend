package com.example.demo.order.dto;

import com.example.demo.menu.dto.OrderMenuUpdateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateReqDto {
    private UUID orderId;
    private UUID storeId;
    private List<OrderMenuUpdateRequestDto> orderMenuUpdateRequestDtos = new ArrayList<>();
    private String address;
    private String memo;
}
