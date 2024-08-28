package com.example.demo.store.repository;

import com.example.demo.store.dto.StoreResponseDto;
import com.example.demo.store.entity.StoreMapping;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.example.demo.category.entity.QCategory.category;
import static com.example.demo.location.entity.QLocation.location;
import static com.example.demo.store.entity.QStore.store;
import static com.example.demo.store.entity.QStoreMapping.storeMapping;

@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryCustom {
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
                .where(store.isDeleted.eq(false))
                .groupBy(store.storeId, store.storeName, store.description, location.address);
        List<Tuple> results = mainQuery.fetch();
        System.out.println();
        Tuple result = results.get(0);
        return tupleToResponse(result);
    }

    @Override
    public Page<StoreResponseDto> getStores(Pageable pageable) {
        List<OrderSpecifier<?>> orders = getAllOrderSpecifiers();
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
                .where(store.isDeleted.eq(false))
                .groupBy(store.storeId, store.storeName, store.description, location.address)
                .orderBy(orders.toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        List<Tuple> results = mainQuery.fetch();
        List<StoreResponseDto> responseDtos = new ArrayList<>();
        for (Tuple result : results) {
            responseDtos.add(tupleToResponse(result));
        }

        JPAQuery<Long> countQuery = queryFactory
                .select(store.count())
                .from(store)
                .where(store.isDeleted.eq(false));
        return new PageImpl<>(responseDtos, pageable,countQuery.fetchCount());

    }

    @Override
    public Page<StoreResponseDto> CategoryStores(UUID categoryId, Pageable pageable) {
        List<OrderSpecifier<?>> orders = getAllOrderSpecifiers();
        JPAQuery<UUID> storeIdsSubQuery = queryFactory
                .select(storeMapping.store.storeId)
                .from(storeMapping)
                .where(storeMapping.category.categoryId.eq(categoryId))
                .where(category.isDeleted.eq(false));
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
                .where(store.isDeleted.eq(false))
                .where(store.storeId.in(storeIdsSubQuery))
                .groupBy(store.storeId, store.storeName, store.description, location.address)
                .orderBy(orders.toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        List<Tuple> results = mainQuery.fetch();
        List<StoreResponseDto> responseDtos = new ArrayList<>();
        for (Tuple result : results) {
            responseDtos.add(tupleToResponse(result));
        }
        JPAQuery<Long> countQuery = queryFactory
                .select(store.count())
                .from(store)
                .leftJoin(storeMapping).on(store.storeId.eq(storeMapping.store.storeId))
                .leftJoin(category).on(storeMapping.category.categoryId.eq(category.categoryId))
                .where(store.isDeleted.eq(false))
                .where(category.categoryId.eq(categoryId));

        return new PageImpl<>(responseDtos, pageable,countQuery.fetchCount());
    }

    @Override
    public Page<StoreResponseDto> locationStores(UUID locationId, Pageable pageable) {
        List<OrderSpecifier<?>> orders = getAllOrderSpecifiers();
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
                .where(store.isDeleted.eq(false))
                .where(location.locationId.eq(locationId))
                .groupBy(store.storeId, store.storeName, store.description, location.address)
                .orderBy(orders.toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        List<Tuple> results = mainQuery.fetch();
        List<StoreResponseDto> responseDtos = new ArrayList<>();
        for (Tuple result : results) {
            responseDtos.add(tupleToResponse(result));
        }

        JPAQuery<Long> countQuery = queryFactory
                .select(store.count())
                .from(store)
                .where(store.isDeleted.eq(false));
        return new PageImpl<>(responseDtos, pageable,countQuery.fetchCount());
    }

    @Override
    public Page<StoreResponseDto> SearchStores(String name, Pageable pageable) {
        List<OrderSpecifier<?>> orders = getAllOrderSpecifiers();
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
                .where(store.isDeleted.eq(false))
                .where(store.storeName.contains(name))
                .groupBy(store.storeId, store.storeName, store.description, location.address)
                .orderBy(orders.toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        List<Tuple> results = mainQuery.fetch();
        List<StoreResponseDto> responseDtos = new ArrayList<>();
        for (Tuple result : results) {
            responseDtos.add(tupleToResponse(result));
        }
        JPAQuery<Long> countQuery = queryFactory
                .select(store.count())
                .from(store)
                .where(store.isDeleted.eq(false))
                .where(store.storeName.contains(name));
        return new PageImpl<>(responseDtos, pageable,countQuery.fetchCount());
    }

    @Override
    public List<StoreMapping> getReferenceStore(UUID storeId) {
        QueryResults<StoreMapping> mainQuery = queryFactory
                .select(storeMapping)
                .from(storeMapping)
                .join(storeMapping.store, store)
                .where(storeMapping.store.storeId.eq(storeId))
                .fetchResults();
        return mainQuery.getResults();
    }


    private List<String> parseCategories(String categories) {
        if (categories == null || categories.isEmpty()) {
            return List.of();
        }
        return  List.of(categories.split(",\\s*"));
    }

    private StoreResponseDto tupleToResponse(Tuple result) {
        UUID storeid = result.get(0, UUID.class);
        String storeName = result.get(1, String.class);
        String description = result.get(2, String.class);
        String location = result.get(3, String.class);
        List<String> category = new ArrayList<>(parseCategories(result.get(4, String.class)));
        Collections.sort(category);
        return new StoreResponseDto(storeid, storeName, description, location, category);
    }

    private List<OrderSpecifier<?>> getAllOrderSpecifiers() {
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        orders.add(new OrderSpecifier<>(Order.ASC, store.createdAt));
        return orders;
    }
}
