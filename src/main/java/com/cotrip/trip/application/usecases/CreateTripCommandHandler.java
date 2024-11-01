package com.cotrip.trip.application.usecases;


import com.cotrip.trip.application.ports.TripRepository;

public class CreateTripCommandHandler {
    private final TripRepository tripRepository;

    public CreateTripCommandHandler(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public void handle(CreateTripCommand command) {
        tripRepository.save(command.getTrip());
    }
}
