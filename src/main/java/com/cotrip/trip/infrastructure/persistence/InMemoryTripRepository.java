package com.cotrip.trip.infrastructure.persistence;

import com.cotrip.trip.application.ports.TripRepository;
import com.cotrip.trip.domain.models.Trip;

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
    public void update(Trip trip) {
        trips.removeIf(t -> t.getId().equals(trip.getId()));
        trips.add(trip);
    }

    @Override
    public Optional<Trip> findById(String id) {
        return trips.stream()
                .filter(trip -> trip.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteAll() {
        trips.clear();
    }
}
