package com.cotrip.trip.application.usecases;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;

public record AddTransportCommand(
        String tripId,
        String type,
        String journey,
        String date,
        String address,
        String city,
        String zipCode,
        String country,
        double priceAmount,
        String priceCurrency
) implements Command<Voidy> {
}
