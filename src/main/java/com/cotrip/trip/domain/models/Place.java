package com.cotrip.trip.domain.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "place")
public class Place {
    @Id
    private String id;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String zipCode;

    @Column
    private String country;

    public Place() {
        this.id = UUID.randomUUID().toString();
    }

    public Place(String address, String city, String zipCode, String country) {
        this.id = UUID.randomUUID().toString();
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }
}
