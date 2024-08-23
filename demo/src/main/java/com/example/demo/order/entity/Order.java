package com.example.demo.order.entity;

import com.example.demo.order.dto.ReqDto;
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

    public Order(ReqDto reqDto){
        this.userId = reqDto.getUserId();
        this.paymentId = reqDto.getPaymentId();
        this.storeId = reqDto.getStoreId();
        this.memo = reqDto.getMemo();
    }
}
