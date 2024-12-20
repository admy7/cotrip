package com.cotrip.trip.api.dtos;

import jakarta.validation.constraints.NotBlank;

public record AddPriceDTO(
        @NotBlank(message = "Currency is mandatory")
        String currency,
        @NotBlank(message = "Amount is mandatory")
        double amount) {}
