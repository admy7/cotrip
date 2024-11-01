package com.cotrip.trip.domain;

import java.time.LocalDate;
import java.util.UUID;

public class Trip {
    private String id;
    private String origin;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;

    public Trip(String origin, String destination, LocalDate startDate, LocalDate endDate) {
        this.id = UUID.randomUUID().toString();
        this.origin = origin;
        this.destination = destination;
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
