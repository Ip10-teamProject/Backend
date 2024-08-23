package com.example.demo.order.service;

import com.example.demo.order.dto.ReqDto;
import com.example.demo.order.entity.Order;
import com.example.demo.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public String create(ReqDto reqDto){
        Order order = orderRepository.save(new Order(reqDto));
        return "hi";
    }
}
