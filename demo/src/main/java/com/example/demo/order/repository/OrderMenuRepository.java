package com.example.demo.order.repository;

import com.example.demo.order.entity.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, UUID> {
    void deleteByOrderId(UUID orderId);
}
