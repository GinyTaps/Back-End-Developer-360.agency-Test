package com.agency.listingcar.exceptions;

public class PublishingLimitReachedException extends RuntimeException {
    public PublishingLimitReachedException(String message) {
        super(message);
    }
}
