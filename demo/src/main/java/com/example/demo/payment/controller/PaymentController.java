package com.example.demo.payment.controller;

import com.example.demo.payment.dto.PaymentResDto;
import com.example.demo.payment.service.PaymentService;
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
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/list")
    public List<PaymentResDto> getList() {
        return paymentService.getList();
    }

    @GetMapping("/{paymentId}")
    public PaymentResDto getOne(@PathVariable UUID paymentId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return paymentService.getOne(paymentId, userDetails);
    }

    @PreAuthorize("hasAnyRole('MASTER')")
    @GetMapping("/search")
    public Page<PaymentResDto> search(  //검색 조건 추가해야 함
                                        @RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size,
                                        @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                                        @RequestParam(value = "isAsc", defaultValue = "true") boolean isAsc
    ) {
        return paymentService.search(page - 1, size, sortBy, isAsc);
    }

    @PreAuthorize("hasAnyRole('MASTER', 'CUSTOMER')")
    @GetMapping("/list/{userId}")
    public List<PaymentResDto> getListByUserId(@PathVariable Long userId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return paymentService.getListByUserId(userId, userDetails);
    }

    @PostMapping("/{orderId}")
    public String create(@PathVariable UUID orderId,
                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        return paymentService.create(userDetails, orderId);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @DeleteMapping("/{paymentId}")
    public String cancel(@PathVariable UUID paymentId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return paymentService.cancel(paymentId, userDetails);
    }

    @PreAuthorize("hasAnyRole('MASTER')")
    @PutMapping("/{paymentId}")
    public String refund(@PathVariable UUID paymentId) {
        return paymentService.refund(paymentId);
    }
}
