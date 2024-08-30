package com.example.demo.order.dto;

import com.example.demo.menu.dto.OrderMenuCreateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderReqDto {
    private UUID storeId;
    private List<OrderMenuCreateRequestDto> orderMenuCreateRequestDtos = new ArrayList<>();
    private String address;
    private String memo;
}
