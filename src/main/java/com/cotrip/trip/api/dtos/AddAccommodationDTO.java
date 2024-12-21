package com.cotrip.trip.api.dtos;

import jakarta.validation.constraints.NotBlank;

public record AddAccommodationDTO(
    @NotBlank(message = "Name is mandatory") String name,
    AddPlaceDTO place,
    @NotBlank(message = "Start date is mandatory") String startDate,
    @NotBlank(message = "End date is mandatory") String endDate,
    AddPriceDTO price) {}
