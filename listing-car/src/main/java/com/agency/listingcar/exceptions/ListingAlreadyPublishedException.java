package com.agency.listingcar.exceptions;

public class ListingAlreadyPublishedException extends RuntimeException {
    public ListingAlreadyPublishedException(String message) {
        super(message);
    }
}
