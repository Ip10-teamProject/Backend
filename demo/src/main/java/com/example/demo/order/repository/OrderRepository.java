package com.example.demo.order.repository;

import com.example.demo.order.entity.Order;
import com.example.demo.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByUserId(Long userId);

    List<Order> findAllByStore(Store store);
}
