package com.inditex.price.infrastructure.persistence.adapter;

import com.inditex.price.domain.model.Price;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class PriceRepositoryImplIntegrationTest {

    @Autowired
    private PriceRepositoryImpl repository;

    @Test
    void shouldLoadPricesFromDatabase() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        List<Price> prices = repository.findApplicablePrices(applicationDate, 35455L, 1L);

        assertThat(prices).isNotEmpty();
        assertThat(prices.get(0).getProductId()).isEqualTo(35455L);
        assertThat(prices.get(0).getBrandId()).isEqualTo(1L);
    }
}
