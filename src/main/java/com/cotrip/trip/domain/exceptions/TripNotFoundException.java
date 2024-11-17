package com.cotrip.trip.domain.exceptions;

public class TripNotFoundException extends RuntimeException {
    public TripNotFoundException() {
        super("Trip not found");
    }
}
