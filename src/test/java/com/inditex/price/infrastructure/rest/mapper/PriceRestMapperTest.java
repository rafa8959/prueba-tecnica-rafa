package com.inditex.price.infrastructure.rest.mapper;

import com.inditex.price.domain.model.Price;
import com.inditex.price.infrastructure.rest.dto.PriceResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PriceRestMapperTest {

    @Test
    void shouldMapPriceToPriceResponse() {
        // Arrange
        Price price = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(2)
                .startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                .priority(1)
                .price(new BigDecimal("25.45"))
                .currency("EUR")
                .build();

        // Act
        PriceResponse response = PriceRestMapper.toResponse(price);

        // Assert
        assertThat(response.getProductId()).isEqualTo(35455L);
        assertThat(response.getBrandId()).isEqualTo(1L);
        assertThat(response.getPriceList()).isEqualTo(2);
        assertThat(response.getPrice()).isEqualByComparingTo("25.45");
        assertThat(response.getCurrency()).isEqualTo("EUR");
    }
}

