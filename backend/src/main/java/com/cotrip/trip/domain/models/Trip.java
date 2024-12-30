package com.cotrip.trip.domain.models;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

import com.cotrip.trip.domain.exceptions.InvalidDateException;
import com.cotrip.trip.domain.exceptions.InvalidTripItineraryException;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "trip")
public class Trip {
  @Id private String id;

  @Column private String origin;

  @Column private String destination;

  @Column private LocalDate startDate;

  @Column private LocalDate endDate;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<Transport> transports;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<Accommodation> accommodations;

  public Trip() {
    this.id = UUID.randomUUID().toString();
  }

  public Trip(String origin, String destination, String startDate, String endDate) {
    requireValidItinerary(origin, destination);
    requireValidDates(startDate, endDate);

    this.id = UUID.randomUUID().toString();
    this.origin = origin;
    this.destination = destination;
    this.startDate = LocalDate.parse(startDate, ISO_LOCAL_DATE);
    this.endDate = LocalDate.parse(endDate, ISO_LOCAL_DATE);
    this.transports = new ArrayList<>();
    this.accommodations = new ArrayList<>();
  }

  private void requireValidItinerary(String origin, String destination) {
    if (origin.equals(destination)) {
      throw new InvalidTripItineraryException();
    }
  }

  private void requireValidDates(String startDate, String endDate) {
    try {
      var parsedStartDate = LocalDate.parse(startDate, ISO_LOCAL_DATE);
      var parsedEndDate = LocalDate.parse(endDate, ISO_LOCAL_DATE);

      if (parsedEndDate.isBefore(parsedStartDate)) {
        throw new InvalidDateException("End date must be after start date", this.getClass());
      }
    } catch (DateTimeParseException e) {
      throw new InvalidDateException(
          "Invalid date format. Format must be YYYY-MM-DD.", this.getClass());
    }
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

  public List<Accommodation> getAccommodations() {
    return accommodations;
  }

  public void addAccommodation(Accommodation accommodation) {
    accommodations.add(accommodation);
  }
}
