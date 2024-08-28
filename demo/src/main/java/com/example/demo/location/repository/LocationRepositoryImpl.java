package com.example.demo.location.repository;

import com.example.demo.location.dto.LocationResponseDto;
import com.example.demo.location.entity.Location;
import com.example.demo.store.entity.Store;
import com.example.demo.store.entity.StoreMapping;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.demo.location.entity.QLocation.location;
import static com.example.demo.store.entity.QStore.store;
import static com.example.demo.store.entity.QStoreMapping.storeMapping;

@RequiredArgsConstructor
public class LocationRepositoryImpl implements LocationRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @Override
    public List<LocationResponseDto> getLocations() {
        QueryResults<Location> results = queryFactory
                .selectFrom(location)
                .fetchResults();
        List<LocationResponseDto> content = new ArrayList<>();
        for (Location location : results.getResults()) {
            content.add(new LocationResponseDto(location.getLocationId(),location.getAddress()));
        }
        return content;
    }

    @Override
    public List<Store> getReferenceStore(UUID locationId) {
        QueryResults<Store> mainQuery = queryFactory
                .select(store)
                .from(store)
                .join(store.location,location)
                .where(store.location.locationId.eq(locationId))
                .fetchResults();
        return mainQuery.getResults();
    }
}
