package com.inditex.price.domain.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.inditex.price.domain.model.Price;

public class PriceDomainService {

	
    public Optional<Price> selectApplicablePrice(List<Price> prices) {
        return prices.stream()
                .max(Comparator.comparing(Price::getPriority));
    }
}
