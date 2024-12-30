package com.cotrip.trip.domain.exceptions;

public class InvalidTripItineraryException extends RuntimeException {
  public InvalidTripItineraryException() {
    super("Destination must be different from origin");
  }
}
