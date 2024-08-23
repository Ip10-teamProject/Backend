package com.example.demo.order.controller;

import com.example.demo.order.dto.ReqDto;
import com.example.demo.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/")
    public String list(){
        return "list";
    }

    @PostMapping("/")
    public String create(@RequestBody ReqDto reqDto){
        orderService.create(reqDto);
        return "hi";
    }
}
