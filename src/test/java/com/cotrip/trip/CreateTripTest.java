package com.cotrip.trip;

import com.cotrip.trip.application.ports.TripRepository;
import com.cotrip.trip.application.usecases.CreateTripCommand;
import com.cotrip.trip.application.usecases.CreateTripCommandHandler;
import com.cotrip.trip.domain.Trip;
import com.cotrip.trip.infrastructure.persistence.InMemoryTripRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class CreateTripTest {
    TripRepository tripRepository = new InMemoryTripRepository();

    @Test
    void shouldCreateNewTrip() {
        var trip = new Trip(
                "Paris",
                "London",
                LocalDate.of(2022, 12, 10),
                LocalDate.of(2022, 12, 15));
        var command = new CreateTripCommand(trip);
        var handler = new CreateTripCommandHandler(tripRepository);

        handler.handle(command);

        var savedTripQuery = tripRepository.findById(trip.getId());
        Assert.assertTrue(savedTripQuery.isPresent());

        var savedTrip = savedTripQuery.get();

        Assert.assertEquals(savedTrip.getId(), trip.getId());
        Assert.assertEquals(savedTrip.getOrigin(), trip.getOrigin());
        Assert.assertEquals(savedTrip.getDestination(), trip.getDestination());
        Assert.assertEquals(savedTrip.getStartDate(), trip.getStartDate());
        Assert.assertEquals(savedTrip.getEndDate(), trip.getEndDate());
    }
}
