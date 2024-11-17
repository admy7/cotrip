package com.cotrip.trip.api.dtos;

import jakarta.validation.constraints.NotBlank;

public record AddTransportDTO(
        @NotBlank(message = "Type is mandatory")
        String type,
        @NotBlank(message = "Journey is mandatory")
        String journey,
        @NotBlank(message = "Date is mandatory")
        String date,
        AddTransportPlaceDTO place,
        AddTransportPriceDTO price) {
}
