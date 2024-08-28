package com.example.demo.store.repository;


import com.example.demo.store.entity.Store;
import com.example.demo.store.entity.StoreMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store,UUID> ,StoreRepositoryCustom{
}
