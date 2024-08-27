package com.example.demo.store.dto;

import com.example.demo.location.dto.LocationResponseDto;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class StoreResponseDto {
    UUID storeId;
    String description;
    String storename;
    String location;
    List<String> category;
    public StoreResponseDto(UUID storeId ,
                            String description ,
                            String storename ,
                            String location,
                            List<String> category){
        this.storeId = storeId;
        this.description = description;
        this.storename = storename;
        this.location = location;
        this.category = category;
    }
}
