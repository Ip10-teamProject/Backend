package com.example.demo.store.dto;

import com.example.demo.menu.dto.MenuCreateRequestDto;
import com.example.demo.menu.dto.MenuUpdateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreMenusUpdateRequestDto {
    private List<MenuUpdateRequestDto> menuUpdateRequestDtoList = new ArrayList<>();
}