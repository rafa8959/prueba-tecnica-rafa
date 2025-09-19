package com.inditex.price.infrastructure.rest.controller;


import com.inditex.price.application.usecase.GetApplicablePriceUseCase;
import com.inditex.price.domain.exception.InvalidPriceException;
import com.inditex.price.domain.exception.PriceNotFoundException;
import com.inditex.price.domain.model.Price;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PriceController.class)
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetApplicablePriceUseCase getApplicablePriceUseCase;
    

    @Test
    void shouldReturnApplicablePrice() throws Exception {
        Price price = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(1)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .priority(0)
                .price(new BigDecimal("35.50"))
                .currency("EUR")
                .build();

        Mockito.when(getApplicablePriceUseCase.execute(any(), any(), any()))
                .thenReturn(price);

        // Act & Assert
        mockMvc.perform(get("/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }
    
    @Test
    void shouldReturnNotFoundWhenPriceNotFound() throws Exception {
        // Arrange
        Mockito.when(getApplicablePriceUseCase.execute(any(), any(), any()))
                .thenThrow(new PriceNotFoundException(1L, 35455L));

        // Act & Assert
        mockMvc.perform(get("/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message")
                        .value("No applicable price found for brand=1, product=35455"))
                .andExpect(jsonPath("$.path").value("/prices"));
    }
    
    @Test
    void shouldReturnBadRequestWhenInvalidPrice() throws Exception {
        // Arrange
        Mockito.when(getApplicablePriceUseCase.execute(any(), any(), any()))
                .thenThrow(new InvalidPriceException("Invalid date range"));

        // Act & Assert
        mockMvc.perform(get("/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Invalid date range"))
                .andExpect(jsonPath("$.path").value("/prices"));
    }
}

