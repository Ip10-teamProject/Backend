package com.example.demo.payment.entity;

import com.example.demo.global.TimeStamped;
import com.example.demo.order.entity.Order;
import com.example.demo.users.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "p_payment")
public class Payment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne()
    @JoinColumn(name = "order_id")
    private Order order;

    @Column
    private Integer price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.COMPLETED;

    public Payment(User user, Order order) {
        this.user = user;
        this.order = order;
        this.price = order.getPrice();
    }
}



