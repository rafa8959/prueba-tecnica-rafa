package com.inditex.price.domain.service;

import com.inditex.price.domain.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PriceDomainServiceTest {

    private PriceDomainService priceDomainService;

    @BeforeEach
    void setUp() {
        priceDomainService = new PriceDomainService();
    }

    private Price createPrice(int priority, BigDecimal price) {
        return Price.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(1)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .priority(priority)
                .price(price)
                .currency("EUR")
                .build();
    }

    @Test
    void shouldReturnEmptyWhenListIsEmpty() {
        Optional<Price> result = priceDomainService.selectApplicablePrice(Collections.emptyList());
        assertTrue(result.isEmpty(), "Expected empty optional when no prices are provided");
    }

    @Test
    void shouldReturnSingleElementWhenListHasOne() {
        Price price = createPrice(0, new BigDecimal("35.50"));
        Optional<Price> result = priceDomainService.selectApplicablePrice(Collections.singletonList(price));
        assertTrue(result.isPresent());
        assertEquals(price, result.get());
    }

    @Test
    void shouldReturnPriceWithHighestPriority() {
        Price lowPriority = createPrice(0, new BigDecimal("35.50"));
        Price highPriority = createPrice(2, new BigDecimal("45.00"));
        Price mediumPriority = createPrice(1, new BigDecimal("40.00"));

        Optional<Price> result = priceDomainService.selectApplicablePrice(
                Arrays.asList(lowPriority, highPriority, mediumPriority)
        );

        assertTrue(result.isPresent());
        assertEquals(highPriority, result.get());
    }

    @Test
    void shouldReturnAnyWhenPrioritiesAreEqual() {
        Price p1 = createPrice(1, new BigDecimal("25.00"));
        Price p2 = createPrice(1, new BigDecimal("30.00"));

        Optional<Price> result = priceDomainService.selectApplicablePrice(Arrays.asList(p1, p2));

        assertTrue(result.isPresent());
        assertTrue(result.get().getPriority().equals(1));
    }
}

