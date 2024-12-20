package com.cotrip.trip.api;

import com.cotrip.CotripBackendApplication;
import com.cotrip.trip.api.dtos.AddTransportDTO;
import com.cotrip.trip.api.dtos.AddPlaceDTO;
import com.cotrip.trip.api.dtos.AddPriceDTO;
import com.cotrip.trip.application.ports.TripRepository;
import com.cotrip.trip.domain.models.Currency;
import com.cotrip.trip.domain.models.Journey;
import com.cotrip.trip.domain.models.TransportType;
import com.cotrip.trip.domain.models.Trip;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@Import(CotripBackendApplication.class)
public class AddTransportCommandE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TripRepository tripRepository;

    private String tripId;

    @BeforeEach
    void setUp() {
        tripRepository.deleteAll();

        var trip = new Trip("Paris",
                "London",
                "2022-12-03",
                "2022-12-08");

        tripRepository.save(trip);
        tripId = trip.getId();
    }


    @Test
    void shouldAddTransportToTrip() throws Exception {
        var dto = new AddTransportDTO(
                "BUS",
                "INWARD",
                "2024-11-01T11:00:00",
                new AddPlaceDTO(
                        "Victoria Coach Station, 164 Buckingham Palace Rd",
                        "London",
                        "SW1W 9TP",
                        "United Kingdom"),
                new AddPriceDTO(
                        "GBP", 34));

        var response = mockMvc
                .perform(MockMvcRequestBuilders.post(String.format("/trips/%s/add-transport", tripId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();

        var tripWithTransport = tripRepository.findById(tripId).get();

        var transport = tripWithTransport.getTransports().get(0);

        Assertions.assertNotNull(transport);
        Assertions.assertEquals(TransportType.BUS, transport.getType());
        Assertions.assertEquals(Journey.INWARD, transport.getJourney());
        Assertions.assertEquals(LocalDateTime.parse("2024-11-01T11:00:00"), transport.getDate());
        Assertions.assertEquals("Victoria Coach Station, 164 Buckingham Palace Rd", transport.getPlace().getAddress());
        Assertions.assertEquals("London", transport.getPlace().getCity());
        Assertions.assertEquals("SW1W 9TP", transport.getPlace().getZipCode());
        Assertions.assertEquals("United Kingdom", transport.getPlace().getCountry());
        Assertions.assertEquals(Currency.GBP, transport.getPrice().getCurrency());
        Assertions.assertEquals(0, BigDecimal.valueOf(34.0).compareTo(transport.getPrice().getAmount()));
    }

    @Test
    void whenTripDoesNotExist_ShouldSendNotFound() throws Exception {
        var dto = new AddTransportDTO(
                "BUS",
                "INWARD",
                "2024-11-01T11:00:00",
                new AddPlaceDTO(
                        "Victoria Coach Station, 164 Buckingham Palace Rd",
                        "London",
                        "SW1W 9TP",
                        "United Kingdom"),
                new AddPriceDTO(
                        "GBP", 34));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/trips/non-existing-trip-id/add-transport")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }
}
