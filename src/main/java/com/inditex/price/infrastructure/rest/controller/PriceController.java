package com.inditex.price.infrastructure.rest.controller;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inditex.price.application.usecase.GetApplicablePriceUseCase;
import com.inditex.price.domain.model.Price;
import com.inditex.price.infrastructure.rest.dto.PriceResponse;
import com.inditex.price.infrastructure.rest.mapper.PriceRestMapper;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/prices")
@RequiredArgsConstructor
public class PriceController {

    private final GetApplicablePriceUseCase getApplicablePriceUseCase;

    @GetMapping
    public ResponseEntity<PriceResponse> getApplicablePrice(
            @RequestParam  @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
            @RequestParam @NotNull @Positive Long productId,
            @RequestParam @NotNull @Positive Long brandId) {

        Price price = getApplicablePriceUseCase.execute(applicationDate, productId, brandId);
        return ResponseEntity.ok(PriceRestMapper.toResponse(price));
    }
}

