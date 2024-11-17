package com.cotrip.trip.application.usecases;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.cotrip.trip.api.dtos.AddTransportPlaceDTO;
import com.cotrip.trip.api.dtos.AddTransportPriceDTO;
import com.cotrip.trip.domain.models.*;

import java.time.LocalDateTime;

public record AddTransportCommand(
        String tripId,
        TransportType type,
        Journey journey,
        LocalDateTime date,
        Place place,
        Money price) implements Command<Voidy> {

    public AddTransportCommand(String tripId, String type, String journey, String date, AddTransportPlaceDTO place, AddTransportPriceDTO price) {
        this(tripId,
                TransportType.valueOf(type.toUpperCase()),
                Journey.valueOf(journey.toUpperCase()),
                LocalDateTime.parse(date),
                new Place(place.address(), place.city(), place.zipCode(), place.country()),
                new Money(price.amount(), Currency.valueOf(price.currency().toUpperCase())));
    }
}
