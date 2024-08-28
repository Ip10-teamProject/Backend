package com.example.demo.menu.entity;

import com.example.demo.global.TimeStamped;
import com.example.demo.menu.dto.MenuCreateRequestDto;
import com.example.demo.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "p_menu")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends TimeStamped implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    public Menu(MenuCreateRequestDto menuCreateRequestDto) {
        this.name = menuCreateRequestDto.getName();
        this.description = menuCreateRequestDto.getDescription();
        this.price = menuCreateRequestDto.getPrice();
    }

}