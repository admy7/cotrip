package com.cotrip.trip.domain.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transport")
public class Transport {
    @Id
    private String id;

    @Column
    private TransportType type;

    @Column
    private Journey journey;

    @Column
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "place_id")
    private Place place;

    @Column
    private Money price;

    public Transport() {
        this.id = UUID.randomUUID().toString();
    }

    public Transport(TransportType type, Journey journey, LocalDateTime date, Place place, Money price) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.journey = journey;
        this.date = date;
        this.place = place;
        this.price = price;
    }

    public Place getPlace() {
        return place;
    }

    public String getId() {
        return id;
    }

    public TransportType getType() {
        return type;
    }

    public Journey getJourney() {
        return journey;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Trip getTrip() {
        return trip;
    }

    public Money getPrice() {
        return price;
    }
}
