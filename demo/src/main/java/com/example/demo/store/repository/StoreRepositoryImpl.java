package com.example.demo.store.repository;

import com.example.demo.store.dto.StoreResponseDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.demo.category.entity.QCategory.category;
import static com.example.demo.location.entity.QLocation.location;
import static com.example.demo.store.entity.QStore.store;
import static com.example.demo.store.entity.QStoreMapping.storeMapping;

@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @Override
    public StoreResponseDto getStore(UUID storeId) {
         JPAQuery<Tuple> mainQuery = queryFactory
                .select(
                        store.storeId,
                        store.storeName,
                        store.description,
                        location.address,
                        Expressions.stringTemplate(
                                "STRING_AGG({0}, ', ')", category.categoryName
                        ).as("categories")
                )
                .from(store)
                .leftJoin(location).on(store.location.locationId.eq(location.locationId))
                .leftJoin(storeMapping).on(store.storeId.eq(storeMapping.store.storeId))
                .leftJoin(category).on(storeMapping.category.categoryId.eq(category.categoryId))
                .where(store.storeId.eq(storeId))
                .groupBy(store.storeId, store.storeName, store.description, location.address);
        List<Tuple> results = mainQuery.fetch();
        Tuple result = results.get(0);
        return tupleToResponse(result);
    }

    @Override
    public List<StoreResponseDto> getStores() {
        JPAQuery<Tuple> mainQuery = queryFactory
                .select(
                        store.storeId,
                        store.storeName,
                        store.description,
                        location.address,
                        Expressions.stringTemplate(
                                "STRING_AGG({0}, ', ')", category.categoryName
                        ).as("categories")
                )
                .from(store)
                .leftJoin(location).on(store.location.locationId.eq(location.locationId))
                .leftJoin(storeMapping).on(store.storeId.eq(storeMapping.store.storeId))
                .leftJoin(category).on(storeMapping.category.categoryId.eq(category.categoryId))
                .groupBy(store.storeId, store.storeName, store.description, location.address);
        List<Tuple> results = mainQuery.fetch();
        List<StoreResponseDto> responseDtos= new ArrayList<>();
        for (Tuple result : results) {
            responseDtos.add(tupleToResponse(result));
        }
        return responseDtos;
    }

    private List<String> parseCategories(String categories) {
        if (categories == null || categories.isEmpty()) {
            return List.of();
        }
        return List.of(categories.split(",\\s*"));
    }
    private StoreResponseDto tupleToResponse(Tuple result){
        UUID storeid = result.get(0, UUID.class);
        String storeName = result.get(1, String.class);
        String description = result.get(2, String.class);
        String location = result.get(3, String.class);
        String categories = result.get(4, String.class);
        return new StoreResponseDto(storeid,storeName,description,location,parseCategories(categories));
    }
}
