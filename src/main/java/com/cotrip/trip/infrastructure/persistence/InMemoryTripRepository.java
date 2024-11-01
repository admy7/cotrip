package com.cotrip.trip.infrastructure.persistence;

import com.cotrip.trip.application.ports.TripRepository;
import com.cotrip.trip.domain.Trip;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryTripRepository implements TripRepository {
    private static final List<Trip> trips = new ArrayList<>();

    @Override
    public void save(Trip trip) {
        trips.add(trip);
    }

    @Override
    public Optional<Trip> findById(String id) {
        return trips.stream()
                .filter(trip -> trip.getId().equals(id))
                .findFirst();
    }
}
