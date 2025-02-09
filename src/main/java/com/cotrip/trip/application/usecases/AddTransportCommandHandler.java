package com.cotrip.trip.application.usecases;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.cotrip.trip.application.ports.TripRepository;
import com.cotrip.trip.domain.exceptions.TripNotFoundException;
import com.cotrip.trip.domain.models.*;

public class AddTransportCommandHandler implements Command.Handler<AddTransportCommand, Voidy> {
  private final TripRepository tripRepository;

  public AddTransportCommandHandler(TripRepository tripRepository) {
    this.tripRepository = tripRepository;
  }

  @Override
  public Voidy handle(AddTransportCommand command) {
    var trip = tripRepository.findById(command.tripId()).orElseThrow(TripNotFoundException::new);

    var newTransport =
        new Transport(
            command.type(),
            command.journey(),
            command.date(),
            new Place(command.address(), command.city(), command.zipCode(), command.country()),
            new Money(command.priceAmount(), command.priceCurrency()));

    trip.addTransport(newTransport);
    tripRepository.update(trip);

    return new Voidy();
  }
}
