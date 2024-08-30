package com.example.demo.payment.dto;

import com.example.demo.order.entity.Order;
import com.example.demo.payment.entity.Payment;
import com.example.demo.payment.entity.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class PaymentResDto {

    private Long userId;
    private UUID orderId;
    private Integer price;
    private PaymentStatus paymentStatus;


    public PaymentResDto(Payment payment) {
        this.userId=payment.getUser().getId();
        this.orderId=payment.getOrder().getId();
        this.price=payment.getPrice();
        this.paymentStatus=payment.getStatus();
    }
}
