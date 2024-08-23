package com.example.demo.order.controller;

import com.example.demo.order.dto.ReqDto;
import com.example.demo.order.dto.ResDto;
import com.example.demo.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/admin") //관리자 관련 항목들은 나중에 따로 api를 생성하는게 좋을거에요. 일단은 이렇게 작성
    public List<ResDto> list(){
        return orderService.getAll();
    }

    @PostMapping("/")
    public ResDto create(@RequestBody ReqDto reqDto){
        return orderService.create(reqDto);
    }
}
