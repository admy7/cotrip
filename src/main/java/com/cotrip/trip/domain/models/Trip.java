package com.cotrip.trip.domain.models;

import com.cotrip.trip.domain.exceptions.InvalidTripDateException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "trips")
public class Trip {
    @Id
    private String id;

    @Column
    private String origin;

    @Column
    private String destination;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    protected Trip() {
    }

    public Trip(String origin, String destination, LocalDate startDate, LocalDate endDate) {
        this.id = UUID.randomUUID().toString();
        this.origin = origin;
        this.destination = destination;

        if (endDate.isBefore(startDate)) {
            throw new InvalidTripDateException();
        }

        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
