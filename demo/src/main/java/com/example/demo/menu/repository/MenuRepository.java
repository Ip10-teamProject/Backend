package com.example.demo.menu.repository;

import com.example.demo.menu.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID> {

    // Menu.store.storeId를 통해 Menu 목록을 조회
    @Query("SELECT m FROM Menu m WHERE m.store.storeId = :storeId AND m.deletedBy IS NULL")
    Page<Menu> findByStoreId(@Param("storeId") UUID storeId, Pageable pageable);

    Optional<Menu> findByName(String name);

    @Query("SELECT m FROM  Menu m WHERE m.name = :name AND m.store.storeId = :storeId AND m.deletedBy IS NULL")
    Optional<Menu> findByMenuNameAndStoreId(@Param("name") String name, @Param("storeId") UUID storeId);

    // 요청 파라미터 menuName을 이용하여 대소문자 구분 없이 Menu 목록을 검색
    @Query("SELECT m FROM  Menu m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :menuName, '%')) AND m.store.storeId = :storeId AND m.deletedBy IS NULL")
    Page<Menu> findByMenuNameAndStoreIdPageable(@Param("menuName") String menuName, @Param("storeId") UUID storeId, Pageable pageable);

    @Query("SELECT m FROM  Menu m WHERE m.id = :menuId AND m.store.storeId = :storeId AND m.deletedBy IS NULL")
    Optional<Menu> findByMenuIdAndStoreId(@Param("menuId") UUID menuId, @Param("storeId") UUID storeId);
}
