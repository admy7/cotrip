package com.cotrip.trip.domain.exceptions;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException(String message, Class clazz) {
        super(clazz.getSimpleName() + ": " + message);
    }
}
