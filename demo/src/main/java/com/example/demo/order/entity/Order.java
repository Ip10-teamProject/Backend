package com.example.demo.order.entity;

import com.example.demo.order.dto.OrderReqDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "p_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private Long userId;

    @Column
    private UUID paymentId;

    @Column
    private UUID storeId;

    @Column
    private String memo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order(OrderReqDto orderReqDto){
        this.userId = orderReqDto.getUserId();
        this.paymentId = orderReqDto.getPaymentId();
        this.storeId = orderReqDto.getStoreId();
        this.memo = orderReqDto.getMemo();
    }

    public void update(OrderReqDto orderReqDto) {
        this.memo = orderReqDto.getMemo();
    }

    public void cancel(){
        if(this.status!=OrderStatus.PENDING){
            throw new IllegalArgumentException("진행중인 주문은 취소할 수 없습니다.");
        }

        this.status = OrderStatus.CANCELED;
    }
}
