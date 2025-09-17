package com.inditex.price.domain.model;

import com.inditex.price.domain.exception.InvalidPriceException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    private static final Long BRAND_ID = 1L;
    private static final Long PRODUCT_ID = 35455L;
    private static final Integer PRICE_LIST = 1;
    private static final LocalDateTime START_DATE = LocalDateTime.of(2020, 6, 14, 0, 0);
    private static final LocalDateTime END_DATE = LocalDateTime.of(2020, 12, 31, 23, 59);
    private static final Integer PRIORITY = 0;
    private static final BigDecimal VALID_PRICE = new BigDecimal("35.50");
    private static final String CURRENCY = "EUR";

    private Price.PriceBuilder validBuilder() {
        return Price.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceList(PRICE_LIST)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .priority(PRIORITY)
                .price(VALID_PRICE)
                .currency(CURRENCY);
    }

    @Test
    void shouldCreateValidPrice() {
        Price price = validBuilder().build();
        assertNotNull(price);
        assertEquals(BRAND_ID, price.getBrandId());
        assertEquals(PRODUCT_ID, price.getProductId());
        assertEquals(PRICE_LIST, price.getPriceList());
        assertEquals(VALID_PRICE, price.getPrice());
        assertEquals(CURRENCY, price.getCurrency());
    }

    @Test
    void shouldThrowWhenBrandIdIsNull() {
        InvalidPriceException ex = assertThrows(InvalidPriceException.class, () ->
            validBuilder().brandId(null).build()
        );
        assertEquals("brandId, productId and priceList are required", ex.getMessage());
    }

    @Test
    void shouldThrowWhenProductIdIsNull() {
        InvalidPriceException ex = assertThrows(InvalidPriceException.class, () ->
            validBuilder().productId(null).build()
        );
        assertEquals("brandId, productId and priceList are required", ex.getMessage());
    }

    @Test
    void shouldThrowWhenPriceListIsNull() {
        InvalidPriceException ex = assertThrows(InvalidPriceException.class, () ->
            validBuilder().priceList(null).build()
        );
        assertEquals("brandId, productId and priceList are required", ex.getMessage());
    }

    @Test
    void shouldThrowWhenStartDateIsAfterEndDate() {
        InvalidPriceException ex = assertThrows(InvalidPriceException.class, () ->
            validBuilder()
                .startDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .endDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .build()
        );
        assertEquals("Invalid date range", ex.getMessage());
    }

    @Test
    void shouldThrowWhenPriorityIsNegative() {
        InvalidPriceException ex = assertThrows(InvalidPriceException.class, () ->
            validBuilder().priority(-1).build()
        );
        assertEquals("Priority must be >= 0", ex.getMessage());
    }

    @Test
    void shouldThrowWhenPriceIsZeroOrNegative() {
        InvalidPriceException ex1 = assertThrows(InvalidPriceException.class, () ->
            validBuilder().price(BigDecimal.ZERO).build()
        );
        assertEquals("Price must be greater than 0", ex1.getMessage());

        InvalidPriceException ex2 = assertThrows(InvalidPriceException.class, () ->
            validBuilder().price(new BigDecimal("-10.00")).build()
        );
        assertEquals("Price must be greater than 0", ex2.getMessage());
    }

    @Test
    void shouldThrowWhenCurrencyIsBlank() {
        InvalidPriceException ex = assertThrows(InvalidPriceException.class, () ->
            validBuilder().currency(" ").build()
        );
        assertEquals("Currency is required", ex.getMessage());
    }
}
