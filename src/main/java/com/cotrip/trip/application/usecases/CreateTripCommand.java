package com.cotrip.trip.application.usecases;

import an.awesome.pipelinr.Command;
import com.cotrip.trip.domain.viewmodels.IdResponse;

public class CreateTripCommand implements Command<IdResponse> {
    private String origin;
    private String destination;
    private String startDate;
    private String endDate;

    public CreateTripCommand(String origin, String destination, String startDate, String endDate) {
        this.origin = origin;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}