package com.cotrip.trip.application.usecases;

import an.awesome.pipelinr.Command;
import com.cotrip.trip.application.ports.TripRepository;
import com.cotrip.trip.domain.models.Trip;
import com.cotrip.trip.domain.viewmodels.IdResponse;

public class CreateTripCommandHandler implements Command.Handler<CreateTripCommand, IdResponse> {
  private final TripRepository tripRepository;

  public CreateTripCommandHandler(TripRepository tripRepository) {
    this.tripRepository = tripRepository;
  }

  public IdResponse handle(CreateTripCommand command) {

    var trip =
        new Trip(command.origin(), command.destination(), command.startDate(), command.endDate());

    tripRepository.save(trip);

    return new IdResponse(trip.getId());
  }
}
