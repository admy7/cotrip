package com.cotrip.trip.domain;

import com.cotrip.trip.domain.exceptions.InvalidDateException;
import com.cotrip.trip.domain.exceptions.InvalidTripItineraryException;
import com.cotrip.trip.domain.models.Trip;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TripTest {
  @Test
  void whenOriginIsDestination_ShouldThrow() {
    var exception =
        Assert.assertThrows(
            InvalidTripItineraryException.class,
            () -> new Trip("Paris", "Paris", "2022-12-01", "2022-12-05"));

    Assert.assertEquals("Destination must be different from origin", exception.getMessage());
  }

  @Test
  void whenReturnDateIsPriorToStartDate_ShouldThrow() {
    var exception =
        Assert.assertThrows(
            InvalidDateException.class,
            () -> new Trip("Paris", "London", "2022-12-10", "2022-12-05"));

    Assert.assertEquals("Trip: End date must be after start date", exception.getMessage());
  }
}
