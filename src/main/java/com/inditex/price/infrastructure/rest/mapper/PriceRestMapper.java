package com.inditex.price.infrastructure.rest.mapper;


import com.inditex.price.domain.model.Price;
import com.inditex.price.infrastructure.rest.dto.PriceResponse;


public class PriceRestMapper {
	
    public static PriceResponse toResponse(Price price) {
    	
        return PriceResponse.builder()
                .productId(price.getProductId())
                .brandId(price.getBrandId())
                .priceList(price.getPriceList())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .price(price.getPrice())
                .currency(price.getCurrency())
                .build();
    }
}
