package com.example.demo.order.controller;

import com.example.demo.order.dto.OrderReqDto;
import com.example.demo.order.dto.OrderResDto;
import com.example.demo.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/admin") //관리자 관련 항목들은 나중에 따로 api를 생성하는게 좋을거에요. 일단은 이렇게 작성
    public List<OrderResDto> list(){
        return orderService.getAll();
    }

    @PostMapping("/")
    public OrderResDto create(@RequestBody OrderReqDto orderReqDto){
        return orderService.create(orderReqDto);
    }

    @PutMapping("/{orderId}")
    public OrderResDto update(@PathVariable UUID orderId, @RequestBody OrderReqDto orderReqDto){
        return orderService.update(orderId, orderReqDto);
    }

    @PutMapping("/{orderId}/cancel")
    public String cancel(@PathVariable UUID orderId){
        return orderService.cancel(orderId);
    }

    @DeleteMapping("/{orderId}")
    public String delete(@PathVariable UUID orderId){
        return orderService.delete(orderId);
    }
}
