package com.cotrip.trip.application.ports;

import com.cotrip.trip.domain.models.Trip;
import java.util.Optional;

public interface TripRepository {
  void save(Trip trip);

  void update(Trip trip);

  Optional<Trip> findById(String id);

  void deleteAll();
}
