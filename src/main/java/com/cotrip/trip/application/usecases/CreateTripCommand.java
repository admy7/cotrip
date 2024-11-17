package com.cotrip.trip.application.usecases;

import an.awesome.pipelinr.Command;
import com.cotrip.trip.domain.viewmodels.IdResponse;

public record CreateTripCommand(
        String origin,
        String destination,
        String startDate,
        String endDate) implements Command<IdResponse> {}