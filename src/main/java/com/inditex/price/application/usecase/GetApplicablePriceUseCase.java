package com.inditex.price.application.usecase;

import com.inditex.price.domain.exception.PriceNotFoundException;
import com.inditex.price.domain.model.Price;
import com.inditex.price.domain.repository.PriceRepository;
import com.inditex.price.domain.service.PriceDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class GetApplicablePriceUseCase {

    private final PriceRepository priceRepository;
    private final PriceDomainService domainService;

    public Price execute(LocalDateTime applicationDate, Long productId, Long brandId) {
        return domainService.selectApplicablePrice(
                priceRepository.findApplicablePrices(applicationDate, productId, brandId)
        ).orElseThrow(() -> new PriceNotFoundException(brandId, productId));
    }
}
