package com.inditex.price.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.inditex.price.domain.exception.InvalidPriceException;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class Price {

	private final Long brandId;
	private final Long productId;
	private final Integer priceList;
	private final LocalDateTime startDate;
	private final LocalDateTime endDate;
	private final Integer priority;
	private final BigDecimal price;
	private final String currency;

	private Price(Long brandId, Long productId, Integer priceList, LocalDateTime startDate, LocalDateTime endDate,
			Integer priority, BigDecimal price, String currency) {

		if (brandId == null || productId == null || priceList == null) {
			throw new InvalidPriceException("brandId, productId and priceList are required");
		}
		if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
			throw new InvalidPriceException("Invalid date range");
		}
		if (priority == null || priority < 0) {
			throw new InvalidPriceException("Priority must be >= 0");
		}
		if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
			throw new InvalidPriceException("Price must be greater than 0");
		}
		if (currency == null || currency.isBlank()) {
			throw new InvalidPriceException("Currency is required");
		}

		this.brandId = brandId;
		this.productId = productId;
		this.priceList = priceList;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priority = priority;
		this.price = price;
		this.currency = currency;
	}
}
