package com.inditex.price.application.usecase;

import com.inditex.price.domain.exception.PriceNotFoundException;
import com.inditex.price.domain.model.Price;
import com.inditex.price.domain.repository.PriceRepository;
import com.inditex.price.domain.service.PriceDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetApplicablePriceUseCaseTest {

    private PriceRepository priceRepository;
    private PriceDomainService domainService;
    private GetApplicablePriceUseCase useCase;

    @BeforeEach
    void setUp() {
        priceRepository = mock(PriceRepository.class);
        domainService = new PriceDomainService();
        useCase = new GetApplicablePriceUseCase(priceRepository, domainService);
    }

    @Test
    void shouldReturnApplicablePrice_whenPriceExists() {
        // given
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price price = Price.builder()
                .brandId(brandId)
                .productId(productId)
                .priceList(1)
                .startDate(applicationDate.minusHours(1))
                .endDate(applicationDate.plusHours(2))
                .priority(0)
                .price(BigDecimal.valueOf(35.50))
                .currency("EUR")
                .build();

        when(priceRepository.findApplicablePrices(applicationDate, productId, brandId))
                .thenReturn(List.of(price));

        // when
        Price result = useCase.execute(applicationDate, productId, brandId);

        // then
        assertNotNull(result);
        assertEquals(productId, result.getProductId());
        assertEquals(brandId, result.getBrandId());
        assertEquals(BigDecimal.valueOf(35.50), result.getPrice());

        verify(priceRepository, times(1))
                .findApplicablePrices(applicationDate, productId, brandId);
    }

    @Test
    void shouldThrowException_whenNoPriceFound() {
        // given
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 21, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        when(priceRepository.findApplicablePrices(applicationDate, productId, brandId))
                .thenReturn(List.of());

        // when / then
        assertThrows(PriceNotFoundException.class,
                () -> useCase.execute(applicationDate, productId, brandId));

        verify(priceRepository, times(1))
                .findApplicablePrices(applicationDate, productId, brandId);
    }
}

