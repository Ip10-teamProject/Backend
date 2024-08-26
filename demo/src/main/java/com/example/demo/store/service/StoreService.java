package com.example.demo.store.service;

import com.example.demo.category.entity.Category;
import com.example.demo.category.repository.CategoryRepository;
import com.example.demo.location.repository.LocationRepository;
import com.example.demo.store.dto.StoreCreateRequestDto;
import com.example.demo.store.entity.Store;
import com.example.demo.store.entity.StoreMapping;
import com.example.demo.store.repository.StoreMappingRepository;
import com.example.demo.store.repository.StoreRepository;
import com.example.demo.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;
    private final StoreMappingRepository storeMappingRepository;
    public void addStore(StoreCreateRequestDto storeCreateRequestDto, User user) {
        Store store=Store.createStore(storeCreateRequestDto.getStoreName(),
                storeCreateRequestDto.getDescription(),
                locationRepository.findById(storeCreateRequestDto.getLocation_id()).get());
        storeRepository.save(store);
        List<StoreMapping> storeMappings = new ArrayList<>();
        for (Long categoryId : storeCreateRequestDto.getCategory_id()) {
            Optional<Category> category = categoryRepository.findById(categoryId);
            category.ifPresent(value -> storeMappings.add(StoreMapping.createStoreMapping(store, value)));
        }
        storeMappingRepository.saveAll(storeMappings);
    }

    public void getStore(Long storeId) { // 카테고리랑 일단 조인해서 카테고리 다 들고오고 메뉴랑 조인해서 메뉴 들고와서 리턴
    }

    public void getStores() { // 카테고리랑 조인해서 그룹바이로 각 상품이 가지고있는 카테고리를 그룹핑한후 그거 데이터로 변환해서 가져오기
    }

    public void updateStore(Long storeId) {
    }

    public void deleteStore(Long storeId) {
    }
}
