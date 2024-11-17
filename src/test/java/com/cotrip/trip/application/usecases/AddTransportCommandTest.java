package com.cotrip.trip.application.usecases;

import com.cotrip.trip.application.ports.TripRepository;
import com.cotrip.trip.domain.exceptions.TripNotFoundException;
import com.cotrip.trip.domain.models.*;
import com.cotrip.trip.infrastructure.persistence.InMemoryTripRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AddTransportCommandTest {
    private final TripRepository tripRepository = new InMemoryTripRepository();
    private String tripId;

    @BeforeEach
    void setUp() {
       tripRepository.deleteAll();
       var trip = new Trip("Paris",
               "London",
               LocalDate.of(2024, 11, 6),
               LocalDate.of(2024, 11, 10));

       tripId = trip.getId();
       tripRepository.save(trip);
    }

    @Test
    void shouldAddTransportToTrip() {
        var command = new AddTransportCommand(
                tripId,
                TransportType.PLANE,
                Journey.OUTWARD,
                LocalDateTime.of(2024, 11, 6, 10, 30),
                new Place("8 Avenue des Champs-Élysées", "Paris", "75000", "France"),
                new Money(350, Currency.EUR)
        );

        var handler = new AddTransportCommandHandler(tripRepository);

        handler.handle(command);

        var trip = tripRepository.findById(tripId).get();
        Assert.assertEquals(1, trip.getTransports().size());

        var transport = trip.getTransports().get(0);
        Assert.assertEquals(TransportType.PLANE, transport.getType());
        Assert.assertEquals(Journey.OUTWARD, transport.getJourney());

        var date = transport.getDate();

        Assert.assertEquals(2024, date.getYear());
        Assert.assertEquals(11, date.getMonthValue());
        Assert.assertEquals(6, date.getDayOfMonth());

        var place = transport.getPlace();
        Assert.assertEquals("8 Avenue des Champs-Élysées", place.getAddress());
        Assert.assertEquals("75000", place.getZipCode());

        var price = transport.getPrice();
        Assert.assertEquals(BigDecimal.valueOf(350.0), price.getAmount());
        Assert.assertEquals(Currency.EUR, price.getCurrency());
    }

    @Test
    void whenTripDoesNotExist_ShouldThrow() {
        var command = new AddTransportCommand(
                "non-existing-trip-id",
                TransportType.PLANE,
                Journey.OUTWARD,
                LocalDateTime.of(2024, 11, 6, 10, 30),
                new Place("8 Avenue des Champs-Élysées", "Paris", "75000", "France"),
                new Money(350, Currency.EUR)
        );

        var handler = new AddTransportCommandHandler(tripRepository);

        var exception = Assert.assertThrows(TripNotFoundException.class, () -> handler.handle(command));
        Assert.assertEquals("Trip not found", exception.getMessage());
    }

}
