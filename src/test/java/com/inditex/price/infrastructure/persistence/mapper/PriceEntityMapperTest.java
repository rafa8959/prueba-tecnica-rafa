package com.inditex.price.infrastructure.persistence.mapper;

import com.inditex.price.domain.model.Price;
import com.inditex.price.infrastructure.persistence.entity.PriceEntity;
import com.inditex.price.infrastructure.persistence.entity.PriceId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PriceEntityMapperTest {

    @Test
    void toDomain_shouldMapAllFieldsCorrectly() {
        // given
        PriceId id = new PriceId(1L, 35455L, 2, LocalDateTime.of(2020, 6, 14, 15, 0));
        PriceEntity entity = PriceEntity.builder()
                .id(id)
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                .priority(1)
                .price(new BigDecimal("25.45"))
                .currency("EUR")
                .build();

        // when
        Price price = PriceEntityMapper.toDomain(entity);

        // then
        assertAll(
                () -> assertEquals(1L, price.getBrandId()),
                () -> assertEquals(35455L, price.getProductId()),
                () -> assertEquals(2, price.getPriceList()),
                () -> assertEquals(LocalDateTime.of(2020, 6, 14, 15, 0), price.getStartDate()),
                () -> assertEquals(LocalDateTime.of(2020, 6, 14, 18, 30), price.getEndDate()),
                () -> assertEquals(1, price.getPriority()),
                () -> assertEquals(new BigDecimal("25.45"), price.getPrice()),
                () -> assertEquals("EUR", price.getCurrency())
        );
    }

    @Test
    void toDomain_shouldThrowException_whenInvalidData() {
        // given: PriceEntity with invalid price <= 0
        PriceId id = new PriceId(1L, 35455L, 1, LocalDateTime.of(2020, 6, 14, 0, 0));
        PriceEntity entity = PriceEntity.builder()
                .id(id)
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                .priority(0)
                .price(BigDecimal.ZERO) // invalid
                .currency("EUR")
                .build();

        // when / then
        assertThrows(RuntimeException.class, () -> PriceEntityMapper.toDomain(entity));
    }
}
