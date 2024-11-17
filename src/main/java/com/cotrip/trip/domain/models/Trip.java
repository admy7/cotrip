package com.cotrip.trip.domain.models;

import com.cotrip.trip.domain.exceptions.InvalidTripDateException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "trip")
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

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<Transport> transports;

    public Trip() {
        this.id = UUID.randomUUID().toString();
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
        this.transports = new ArrayList<>();
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

    public List<Transport> getTransports() {
        return transports;
    }

    public void addTransport(Transport transport) {
        transports.add(transport);
    }
}

