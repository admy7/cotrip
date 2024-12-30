package com.cotrip.trip.application.usecases;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;

public record AddAccommodationCommand(
    String tripId,
    String name,
    String address,
    String city,
    String zipCode,
    String country,
    String startDate,
    String endDate,
    double priceAmount,
    String priceCurrency)
    implements Command<Voidy> {}
