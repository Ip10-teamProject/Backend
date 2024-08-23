package com.example.demo.order.service;

import com.example.demo.order.dto.ReqDto;
import com.example.demo.order.dto.ResDto;
import com.example.demo.order.entity.Order;
import com.example.demo.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public ResDto create(ReqDto reqDto){
        Order order = orderRepository.save(new Order(reqDto));
        return new ResDto(order);
    }

    public List<ResDto> getAll(){
        List<Order> orderList = orderRepository.findAll();
        List<ResDto> resDtoList = new ArrayList<>();

        for (Order order: orderList) {
            resDtoList.add((new ResDto(order)));
        }

        return resDtoList;
    }
}
