package com.cotrip.trip.domain.models;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

import com.cotrip.trip.domain.exceptions.InvalidDateException;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@Entity
@Table(name = "accommodation")
public class Accommodation {
  @Id private String id;

  @Column private String name;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "place_id")
  private Place place;

  @Column private LocalDateTime startDate;

  @Column private LocalDateTime endDate;

  private Money price;

  @ManyToOne
  @JoinColumn(name = "trip_id")
  private Trip trip;

  public Accommodation(String name, Place place, String startDate, String endDate, Money price) {
    requireValidDates(startDate, endDate);

    this.id = UUID.randomUUID().toString();
    this.name = name;
    this.place = place;
    this.startDate = LocalDateTime.parse(startDate, ISO_DATE_TIME);
    this.endDate = LocalDateTime.parse(endDate, ISO_DATE_TIME);
    this.price = price;
  }

  private void requireValidDates(String startDate, String endDate) {
    try {
      var parsedStartDate = LocalDateTime.parse(startDate, ISO_DATE_TIME);
      var parsedEndDate = LocalDateTime.parse(endDate, ISO_DATE_TIME);

      if (parsedEndDate.isBefore(parsedStartDate)) {
        throw new InvalidDateException("End date must be after start date", this.getClass());
      }
    } catch (DateTimeParseException e) {
      throw new InvalidDateException(
          "Invalid date time format. It must be YYYY-MM-DDTHH:mm:ss", this.getClass());
    }
  }

  public Accommodation() {
    this.id = UUID.randomUUID().toString();
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Place getPlace() {
    return place;
  }

  public LocalDateTime getStartDate() {
    return startDate;
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }

  public Money getPrice() {
    return price;
  }
}
