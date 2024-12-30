package com.cotrip.trip.application.usecases;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.cotrip.trip.application.ports.TripRepository;
import com.cotrip.trip.domain.exceptions.TripNotFoundException;
import com.cotrip.trip.domain.models.Accommodation;
import com.cotrip.trip.domain.models.Money;
import com.cotrip.trip.domain.models.Place;

public class AddAccommodationCommandHandler
    implements Command.Handler<AddAccommodationCommand, Voidy> {

  private TripRepository tripRepository;

  public AddAccommodationCommandHandler(TripRepository tripRepository) {
    this.tripRepository = tripRepository;
  }

  @Override
  public Voidy handle(AddAccommodationCommand command) {
    var trip =
        tripRepository.findById(command.tripId()).orElseThrow(() -> new TripNotFoundException());

    var newAccommodation =
        new Accommodation(
            command.name(),
            new Place(command.address(), command.city(), command.zipCode(), command.country()),
            command.startDate(),
            command.endDate(),
            new Money(command.priceAmount(), command.priceCurrency()));

    trip.addAccommodation(newAccommodation);

    tripRepository.update(trip);

    return new Voidy();
  }
}
