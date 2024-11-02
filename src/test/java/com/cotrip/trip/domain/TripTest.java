package com.cotrip.trip.domain;

import com.cotrip.trip.domain.exceptions.InvalidTripDateException;
import com.cotrip.trip.domain.models.Trip;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TripTest {
    @Test
    void whenReturnDateIsPriorToStartDate_ShouldThrow() {
        var exception = Assert.assertThrows(InvalidTripDateException.class, () ->  new Trip(
                "Paris",
                "London",
                LocalDate.of(2022, 12, 10),
                LocalDate.of(2022, 12, 5)));

        Assert.assertEquals("End date must be after start date", exception.getMessage());
    }
}
