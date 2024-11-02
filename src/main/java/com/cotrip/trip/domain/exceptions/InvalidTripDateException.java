package com.cotrip.trip.domain.exceptions;

public class InvalidTripDateException extends RuntimeException {
    public InvalidTripDateException() {
        super("End date must be after start date");
    }
}
