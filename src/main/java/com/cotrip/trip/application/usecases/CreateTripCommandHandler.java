package com.cotrip.trip.application.usecases;

import an.awesome.pipelinr.Command;
import com.cotrip.trip.application.ports.TripRepository;
import com.cotrip.trip.domain.models.Trip;
import com.cotrip.trip.domain.viewmodels.IdResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CreateTripCommandHandler implements Command.Handler<CreateTripCommand, IdResponse> {
    private final TripRepository tripRepository;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public CreateTripCommandHandler(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public IdResponse handle(CreateTripCommand command) {
        LocalDate startDate;
        LocalDate endDate;

        try {
            startDate = LocalDate.parse(command.getStartDate(), dateFormatter);
            endDate = LocalDate.parse(command.getEndDate(), dateFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd.");
        }

        var trip = new Trip(command.getOrigin(),
                command.getDestination(),
                startDate,
                endDate);

        tripRepository.save(trip);

        return new IdResponse(trip.getId());
    }
}
