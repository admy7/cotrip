package com.cotrip.trip.api.dtos;

import jakarta.validation.constraints.NotBlank;

public record AddTransportPriceDTO(
        @NotBlank(message = "Currency is mandatory")
        String currency,
        @NotBlank(message = "Amount is mandatory")
        double amount) {}
