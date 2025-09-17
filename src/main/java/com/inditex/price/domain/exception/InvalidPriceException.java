package com.inditex.price.domain.exception;

public class InvalidPriceException extends RuntimeException {
 
	private static final long serialVersionUID = 1L;

	public InvalidPriceException(String message) {
        super(message);
    }
}

