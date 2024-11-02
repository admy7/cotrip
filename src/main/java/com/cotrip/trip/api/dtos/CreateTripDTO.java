package com.cotrip.trip.api.dtos;

import jakarta.validation.constraints.NotBlank;

public class CreateTripDTO {
    @NotBlank(message = "Origin is mandatory")
    private String origin;
    @NotBlank(message = "Destination is mandatory")
    private String destination;
    @NotBlank(message = "Start date is mandatory")
    private String startDate;
    @NotBlank(message = "End date is mandatory")
    private String endDate;

    public CreateTripDTO(String origin, String destination, String startDate, String endDate) {
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
