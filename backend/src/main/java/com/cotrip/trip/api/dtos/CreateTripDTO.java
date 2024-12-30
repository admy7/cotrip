package com.cotrip.trip.api.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateTripDTO(
    @NotBlank(message = "Origin is mandatory") String origin,
    @NotBlank(message = "Destination is mandatory") String destination,
    @NotBlank(message = "Start date is mandatory") String startDate,
    @NotBlank(message = "End date is mandatory") String endDate) {}
