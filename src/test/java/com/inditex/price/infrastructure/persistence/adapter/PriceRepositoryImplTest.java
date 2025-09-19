package com.inditex.price.infrastructure.persistence.adapter;

import com.inditex.price.domain.model.Price;
import com.inditex.price.infrastructure.persistence.entity.PriceEntity;
import com.inditex.price.infrastructure.persistence.entity.PriceId;
import com.inditex.price.infrastructure.persistence.repository.SpringDataPriceRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PriceRepositoryImplTest {

    private final SpringDataPriceRepository jpaRepository = mock(SpringDataPriceRepository.class);
    private final PriceRepositoryImpl adapter = new PriceRepositoryImpl(jpaRepository);

    @Test
    void shouldReturnMappedPrices_whenRepositoryReturnsEntities() {
        // given
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        PriceId id = new PriceId(1L, 35455L, 1, applicationDate.minusHours(1));
        PriceEntity entity = PriceEntity.builder()
                .id(id)
                .endDate(applicationDate.plusHours(5))
                .priority(0)
                .price(new BigDecimal("35.50"))
                .currency("EUR")
                .build();

        when(jpaRepository.findApplicablePrices(35455L, 1L, applicationDate))
                .thenReturn(List.of(entity));

        // when
        List<Price> prices = adapter.findApplicablePrices(applicationDate, 35455L, 1L);

        // then
        assertThat(prices).hasSize(1);
        assertThat(prices.get(0).getProductId()).isEqualTo(35455L);
        assertThat(prices.get(0).getPrice()).isEqualByComparingTo("35.50");

        verify(jpaRepository, times(1))
                .findApplicablePrices(35455L, 1L, applicationDate);
    }

    @Test
    void shouldReturnEmptyList_whenRepositoryReturnsEmpty() {
        // given
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        when(jpaRepository.findApplicablePrices(35455L, 1L, applicationDate))
                .thenReturn(List.of());

        // when
        List<Price> prices = adapter.findApplicablePrices(applicationDate, 35455L, 1L);

        // then
        assertThat(prices).isEmpty();
    }
}
