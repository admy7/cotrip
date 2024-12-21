package com.cotrip.trip.application.usecases;

import com.cotrip.trip.application.ports.TripRepository;
import com.cotrip.trip.domain.exceptions.InvalidDateException;
import com.cotrip.trip.infrastructure.persistence.InMemoryTripRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateTripCommandTest {
  TripRepository tripRepository = new InMemoryTripRepository();

  @BeforeEach
  void setup() {
    tripRepository.deleteAll();
  }

  @Test
  void shouldCreateNewTrip() {
    var command = new CreateTripCommand("Paris", "London", "2022-10-12", "2022-10-15");
    var handler = new CreateTripCommandHandler(tripRepository);

    var response = handler.handle(command);

    var savedTripQuery = tripRepository.findById(response.getId());
    Assert.assertTrue(savedTripQuery.isPresent());

    var savedTrip = savedTripQuery.get();

    Assert.assertEquals(savedTrip.getId(), response.getId());
    Assert.assertEquals(savedTrip.getOrigin(), command.origin());
    Assert.assertEquals(savedTrip.getDestination(), command.destination());
    Assert.assertEquals(savedTrip.getStartDate().toString(), command.startDate());
    Assert.assertEquals(savedTrip.getEndDate().toString(), command.endDate());
  }

  @Test
  void whenDateIsInWrongFormat_ShouldThrow() {
    var command = new CreateTripCommand("Paris", "London", "2022-15-10", "2022-18-10");
    var handler = new CreateTripCommandHandler(tripRepository);

    var message = Assert.assertThrows(InvalidDateException.class, () -> handler.handle(command));

    Assert.assertEquals(
        "Trip: Invalid date format. Format must be YYYY-MM-DD.", message.getMessage());
  }
}
