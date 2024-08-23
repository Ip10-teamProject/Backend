package com.example.demo.order.service;

import com.example.demo.order.dto.OrderReqDto;
import com.example.demo.order.dto.OrderResDto;
import com.example.demo.order.entity.Order;
import com.example.demo.order.entity.OrderStatus;
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
    public OrderResDto create(OrderReqDto orderReqDto) {
        Order order = orderRepository.save(new Order(orderReqDto));
        return new OrderResDto(order);
    }

    public List<OrderResDto> getAll() {
        List<Order> orderList = orderRepository.findAll();
        List<OrderResDto> orderResDtoList = new ArrayList<>();

        for (Order order : orderList) {
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

    @Transactional
    public String delete(UUID orderId) {
        Order order = existOrder(orderId);

        if (order.getStatus() != OrderStatus.COMPLETED && order.getStatus() != OrderStatus.CANCELED) {
            throw new IllegalArgumentException("완료,취소되지 않은 주문은 삭제할 수 없습니다.");
        }

        orderRepository.delete(order);

        return "주문 삭제가 완료되었습니다.";
    }

    public OrderResDto getOne(UUID orderId) {
        return new OrderResDto(existOrder(orderId));
    }

    private Order existOrder(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(
            () -> new IllegalArgumentException("글로벌 예외처리 추가해야함")
        );
    }
}
