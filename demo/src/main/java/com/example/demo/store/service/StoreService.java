package com.example.demo.store.service;

import com.example.demo.category.entity.Category;
import com.example.demo.category.repository.CategoryRepository;
import com.example.demo.location.entity.Location;
import com.example.demo.location.repository.LocationRepository;
import com.example.demo.store.dto.StoreCreateRequestDto;
import com.example.demo.store.dto.StoreResponseDto;
import com.example.demo.store.dto.StoreUpdateRequestDto;
import com.example.demo.store.entity.Store;
import com.example.demo.store.entity.StoreMapping;
import com.example.demo.store.repository.StoreMappingRepository;
import com.example.demo.store.repository.StoreRepository;
import com.example.demo.users.domain.User;
import com.example.demo.users.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;
    private final StoreMappingRepository storeMappingRepository;
    private final UserRepository userRepository;
    @Transactional
    public StoreResponseDto addStore(StoreCreateRequestDto storeCreateRequestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->
            new NullPointerException(""));
        Store store=Store.createStore(storeCreateRequestDto.getStoreName(),
                storeCreateRequestDto.getDescription(),
                locationRepository.findById(storeCreateRequestDto.getLocationId()).get(),user);
        storeRepository.save(store);

        List<StoreMapping> storeMappings = new ArrayList<>();
        for (UUID categoryId : storeCreateRequestDto.getCategoryId()) {
            Optional<Category> category = categoryRepository.findById(categoryId);
            category.ifPresent(value -> storeMappings.add(StoreMapping.createStoreMapping(store, value)));
        }
        storeMappingRepository.saveAll(storeMappings);
        return storeRepository.getStore(store.getStoreId());
    }

    public StoreResponseDto getStore(UUID storeId) { // 카테고리랑 일단 조인해서 카테고리 다 들고오고 메뉴랑 조인해서 메뉴 들고와서 리턴
        return storeRepository.getStore(storeId);
    }

    public Page<StoreResponseDto> getStores(Pageable pageable) { // 카테고리랑 조인해서 그룹바이로 각 상품이 가지고있는 카테고리를 그룹핑한후 그거 데이터로 변환해서 가져오기
        return storeRepository.getStores(pageable);
    }

    @Transactional
    public StoreResponseDto updateStore(UUID storeId, StoreUpdateRequestDto storeUpdateRequestDto) {
        Store store =storeRepository.findById(storeId)
                .filter(o -> !o.isDeleted())
                .orElseThrow(() ->
                new NullPointerException("해당가게없음")
        );
        store.updateStore(storeUpdateRequestDto.getStoreName(),storeUpdateRequestDto.getDescription());
        storeRepository.save(store);
        return storeRepository.getStore(store.getStoreId());
    }

    @Transactional
    public void deleteStore(UUID storeId) {
        Store store =storeRepository.findById(storeId)
                .filter(o -> !o.isDeleted())
                .orElseThrow(() ->
                new NullPointerException("해당가게없음")
        );
        store.deleteStore(); // 맵핑 연관된거 null로 변경
        List<StoreMapping>storeMappings = storeRepository.getReferenceStore(store.getStoreId());
        for (StoreMapping storeMapping : storeMappings) {
            storeMapping.storeClear();
        }
        storeRepository.save(store);
        storeMappingRepository.saveAll(storeMappings);
    }

    public Page<StoreResponseDto> categoryStores(String name, Pageable pageable) {
        Category category=categoryRepository.findBycategoryName(name)
                .filter(o -> !o.isDeleted())
                .orElseThrow(() ->
                        new NullPointerException("해당카테고리없음")
                );
        return storeRepository.CategoryStores(category.getCategoryId(),pageable);
    }

    public Page<StoreResponseDto> locationStores(String name, Pageable pageable) {
        Location location = locationRepository.findByaddress(name)
                .filter(o -> !o.isDeleted())
                .orElseThrow(() ->
                        new NullPointerException("해당지역없음")
                );
        return storeRepository.locationStores(location.getLocationId(),pageable);
    }

    public Page<StoreResponseDto> getSearchStores(String name, Pageable pageable) {
        return storeRepository.SearchStores(name,pageable);
    }
}
