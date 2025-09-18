package com.inditex.price.infrastructure.persistence.mapper;

import com.inditex.price.domain.model.Price;
import com.inditex.price.infrastructure.persistence.entity.PriceEntity;

public class PriceEntityMapper {

    public static Price toDomain(PriceEntity entity) {
        return Price.builder()
                .brandId(entity.getId().getBrandId())
                .productId(entity.getId().getProductId())
                .priceList(entity.getId().getPriceList())
                .startDate(entity.getId().getStartDate())
                .endDate(entity.getEndDate())
                .priority(entity.getPriority())
                .price(entity.getPrice())
                .currency(entity.getCurrency())
                .build();
    }
}
