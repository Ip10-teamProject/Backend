package com.example.demo.menu.controller;

import com.example.demo.menu.dto.MenuCreateRequestDto;
import com.example.demo.menu.dto.MenuCreateResponseDto;
import com.example.demo.menu.service.MenuService;
import com.example.demo.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<MenuCreateResponseDto> createMenu(@RequestBody MenuCreateRequestDto menuCreateRequestDto){
        MenuCreateResponseDto menuCreateResponseDto = menuService.createMenu(menuCreateRequestDto);
        return ResponseEntity.ok(menuCreateResponseDto);
    }

}