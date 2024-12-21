package com.cotrip.trip.api.dtos;

import jakarta.validation.constraints.NotBlank;

public record AddPlaceDTO(
    @NotBlank(message = "Address is mandatory") String address,
    @NotBlank(message = "City is mandatory") String city,
    @NotBlank(message = "Zip code is mandatory") String zipCode,
    @NotBlank(message = "Country is mandatory") String country) {}
