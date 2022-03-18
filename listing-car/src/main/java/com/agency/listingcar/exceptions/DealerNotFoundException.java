package com.agency.listingcar.exceptions;

public class DealerNotFoundException extends RuntimeException {
    public DealerNotFoundException(String message) {
        super(message);
    }
}
