package com.example.demo.category.repository;

import com.example.demo.category.dto.CategoryResponseDto;
import com.example.demo.category.entity.Category;
import com.example.demo.store.entity.StoreMapping;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.demo.category.entity.QCategory.category;
import static com.example.demo.store.entity.QStoreMapping.storeMapping;

@RequiredArgsConstructor

public class CategoryRepositoryImpl implements CategoryRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<CategoryResponseDto> getCategorys() {
        QueryResults<Category> results = queryFactory
                .selectFrom(category)
                .fetchResults();
        List<CategoryResponseDto> content = new ArrayList<>();
        for (Category category : results.getResults()) {
            content.add(new CategoryResponseDto(category.getCategoryId(),category.getCategoryName()));
        }
        return content;
    }

    @Override
    public List<StoreMapping> getReferenceCategory(UUID categoryId) {
        QueryResults<StoreMapping> mainQuery = queryFactory
                .select(storeMapping)
                .from(storeMapping)
                .join(storeMapping.category,category)
                .where(storeMapping.category.categoryId.eq(categoryId))
                .fetchResults();
        return mainQuery.getResults();
    }
}
