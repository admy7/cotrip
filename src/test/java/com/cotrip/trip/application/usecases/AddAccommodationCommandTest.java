package com.cotrip.trip.application.usecases;

import com.cotrip.trip.application.ports.TripRepository;
import com.cotrip.trip.domain.models.Trip;
import com.cotrip.trip.infrastructure.persistence.InMemoryTripRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddAccommodationCommandTest {
    private TripRepository tripRepository = new InMemoryTripRepository();
    String tripId;

    @BeforeEach
    void setUp() {
        var trip = new Trip(
                "Paris",
                "London",
                "2024-12-10",
                "2024-12-15");
        tripId = trip.getId();
        tripRepository.save(trip);
    }

    @Test
    void shouldAddAccommodation() {
        var command = new AddAccommodationCommand(
                tripId,
                "Britannia International Hotel",
                "163 Marsh Wall",
                "London",
                "E14 9SJ",
                "United Kingdom",
                "2025-01-12T15:00:00",
                "2025-01-15T12:00:00",
                560,
                "GBP"
        );
        var commandHandler = new AddAccommodationCommandHandler(tripRepository);

        commandHandler.handle(command);

        var trip = tripRepository.findById(tripId).get();

        Assertions.assertEquals(1, trip.getAccommodations().size());
        Assertions.assertEquals("Britannia International Hotel", trip.getAccommodations().get(0).getName());
    }
}
