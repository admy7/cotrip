package com.cotrip.trip.application.ports;

import com.cotrip.trip.domain.Trip;

import java.util.Optional;

public interface TripRepository {
    void save(Trip trip);
    Optional<Trip> findById(String id);
}
