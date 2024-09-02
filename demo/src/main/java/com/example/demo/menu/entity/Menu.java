package com.example.demo.menu.entity;

import com.example.demo.global.TimeStamped;
import com.example.demo.menu.dto.MenuCreateRequestDto;
import com.example.demo.order.entity.OrderMenu;
import com.example.demo.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
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
    @JoinColumn(name = "store_id")
    private Store store;

    @Column
    private Integer stock;

    @Column
    private Boolean outOfStock;

    @OneToMany(mappedBy = "menu")
    private List<OrderMenu> orderMenus;

    public Menu(MenuCreateRequestDto menuCreateRequestDto) {
        this.name = menuCreateRequestDto.getName();
        this.description = menuCreateRequestDto.getDescription();
        this.price = menuCreateRequestDto.getPrice();
    }

    public void minusStock() {
        this.stock--;
        if (this.stock == 0) {
            this.outOfStock = true;
        }
    }

    public void checkStock(Integer amount) {
        int remainStock = this.stock-amount;
        if(remainStock <0){
            throw new IllegalArgumentException("재고가 부족합니다.");
        }

        this.stock = remainStock;

        if(remainStock == 0){
            this.outOfStock = true;
        }
    }

}