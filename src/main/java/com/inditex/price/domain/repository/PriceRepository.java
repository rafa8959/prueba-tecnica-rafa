package com.inditex.price.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.inditex.price.domain.model.Price;

public interface PriceRepository {

    /**
     * Finds the applicable prices for a product and brand at a given date.
     *
     * @param applicationDate application date
     * @param productId product identifier
     * @param brandId brand identifier
     * @return list of prices that match the conditions
     */
	List<Price> findApplicablePrices(LocalDateTime applicationDate, Long productId, Long brandId);
}
