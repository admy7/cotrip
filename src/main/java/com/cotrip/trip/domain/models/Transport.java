package com.cotrip.trip.domain.models;

import com.cotrip.trip.domain.exceptions.InvalidDateException;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

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

    public Transport(String type, String journey, String date, Place place, Money price) {
        requireValidDateTime(date);

        this.id = UUID.randomUUID().toString();
        this.type = TransportType.valueOf(type.toUpperCase());
        this.journey = Journey.valueOf(journey.toUpperCase());
        this.date = LocalDateTime.parse(date, ISO_DATE_TIME);
        this.place = place;
        this.price = price;
    }

    private void requireValidDateTime(String date) {
        try {
            LocalDateTime.parse(date, ISO_DATE_TIME);
        } catch (Exception e) {
            throw new InvalidDateException("Invalid date time format. It must be YYYY-MM-DDTHH:mm:ss", this.getClass());
        }
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
