package com.example.demo.order.controller;

import com.example.demo.order.dto.OrderReqDto;
import com.example.demo.order.dto.OrderResDto;
import com.example.demo.order.dto.OrderStatusReqDto;
import com.example.demo.order.dto.OrderUpdateReqDto;
import com.example.demo.order.service.OrderService;
import com.example.demo.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasAnyRole('MASTER')")
    @GetMapping("/master")
    public List<OrderResDto> list() {
        return orderService.getAll();
    }

    @PreAuthorize("hasAnyRole('MASTER', 'OWNER')")
    @PutMapping("/{orderId}/status")
    public String changeStatus(
        @PathVariable UUID orderId,
        @RequestBody OrderStatusReqDto orderStatusReqDto,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        return orderService.changeStatus(orderId, userDetails, orderStatusReqDto);
    }

    @PreAuthorize("hasAnyRole('OWNER')")
    @GetMapping("/owner/{storeId}") //store 컨트롤러에서 작성해야 될 것 같음
    public List<OrderResDto> getListMyStore(@PathVariable UUID storeId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return orderService.getListByMyStore(storeId, userDetails);
    }

    @GetMapping("/")
    public List<OrderResDto> getListByUserId(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return orderService.getListByUserId(userDetails);
    }

    @GetMapping("/{orderId}")
    public OrderResDto getOne(@PathVariable UUID orderId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return orderService.getOne(orderId, userDetails);
    }

    @GetMapping("/search")
    public Page<OrderResDto> search(
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
        @RequestParam(value = "isAsc", defaultValue = "true") boolean isAsc
    ) {
        return orderService.search(page - 1, size, sortBy, isAsc);
    }

    @PostMapping("/")
    public String create(@RequestBody OrderReqDto orderReqDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return orderService.create(orderReqDto, userDetails);
    }

    @PutMapping("/{orderId}")
    public OrderResDto update(@PathVariable UUID orderId, @RequestBody OrderUpdateReqDto orderUpdateReqDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return orderService.update(orderId, orderUpdateReqDto, userDetails);
    }

    @PutMapping("/{orderId}/cancel")
    public String cancel(@PathVariable UUID orderId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return orderService.cancel(orderId, userDetails);
    }

    @DeleteMapping("/{orderId}")
    public String delete(@PathVariable UUID orderId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return orderService.delete(orderId, userDetails);
    }
}
