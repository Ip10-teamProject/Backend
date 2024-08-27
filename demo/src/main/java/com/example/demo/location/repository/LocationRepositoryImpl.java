package com.example.demo.location.repository;

import com.example.demo.location.dto.LocationResponseDto;
import com.example.demo.location.entity.Location;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.location.entity.QLocation.location;
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
            content.add(new LocationResponseDto(location.getLocation_id(),location.getAddress()));
        }
        return content;
    }
}
