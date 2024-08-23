package com.example.demo.order.service;

import com.example.demo.order.dto.OrderReqDto;
import com.example.demo.order.dto.OrderResDto;
import com.example.demo.order.entity.Order;
import com.example.demo.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderResDto create(OrderReqDto orderReqDto){
        Order order = orderRepository.save(new Order(orderReqDto));
        return new OrderResDto(order);
    }

    public List<OrderResDto> getAll(){
        List<Order> orderList = orderRepository.findAll();
        List<OrderResDto> orderResDtoList = new ArrayList<>();

        for (Order order: orderList) {
            orderResDtoList.add((new OrderResDto(order)));
        }

        return orderResDtoList;
    }

    @Transactional
    public OrderResDto update(UUID orderId, OrderReqDto orderReqDto) {
        Order order = existOrder(orderId);

        order.update(orderReqDto);

        return new OrderResDto(order);
    }

    @Transactional
    public String cancel(UUID orderId) {
        Order order = existOrder(orderId);

        order.cancel();

        return "주문이 취소되었습니다.";
    }

    private Order existOrder(UUID orderId){
        return orderRepository.findById(orderId).orElseThrow(
            () -> new IllegalArgumentException("글로벌 예외처리 추가해야함")
        );
    }
}
