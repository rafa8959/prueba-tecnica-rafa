package com.inditex.price.domain.exception;

public class PriceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PriceNotFoundException(Long brandId, Long productId) {
        super("No applicable price found for brand=" + brandId + ", product=" + productId);
    }
}
