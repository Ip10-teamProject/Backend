package com.example.demo.store.repository;


import com.example.demo.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store,UUID> ,StoreRepositoryCustom{

    Optional<Store> findByStoreName(String storeName);
}
