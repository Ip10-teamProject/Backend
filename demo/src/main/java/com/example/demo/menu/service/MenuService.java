package com.example.demo.menu.service;

import com.example.demo.menu.dto.MenuCreateRequestDto;
import com.example.demo.menu.dto.MenuCreateResponseDto;
import com.example.demo.menu.entity.Menu;
import com.example.demo.menu.repository.MenuRepository;
import com.example.demo.store.entity.Store;
import com.example.demo.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public MenuCreateResponseDto createMenu(MenuCreateRequestDto menuCreateRequestDto) {
        Menu menu = new Menu(menuCreateRequestDto);
        Optional<Store> optionalStore = storeRepository.findById(menuCreateRequestDto.getStoreId());

        if (optionalStore.isEmpty()) {
            throw new NoSuchElementException("Store id #" + menuCreateRequestDto.getStoreId() + " not found.");
        }

        menu.setStore(optionalStore.get());
        menu.setCreatedBy(menu.getStore().getUser().getUsername());
        menu.setUpdatedBy(menu.getStore().getUser().getUsername());

        Menu savedMenu = menuRepository.save(menu);
        return MenuCreateResponseDto.fromEntity(savedMenu);
    }
}
