package com.cotrip.trip.api;

import com.cotrip.CotripBackendApplication;
import com.cotrip.trip.api.dtos.AddTransportDTO;
import com.cotrip.trip.api.dtos.AddTransportPlaceDTO;
import com.cotrip.trip.api.dtos.AddTransportPriceDTO;
import com.cotrip.trip.application.ports.TripRepository;
import com.cotrip.trip.domain.models.Currency;
import com.cotrip.trip.domain.models.Journey;
import com.cotrip.trip.domain.models.TransportType;
import com.cotrip.trip.domain.models.Trip;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
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
import java.time.LocalDate;
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
                LocalDate.of(2024, 12, 3),
                LocalDate.of(2024, 12, 8));

        tripRepository.save(trip);
        tripId = trip.getId();
    }


    @Test
    void shouldAddTransportToTrip() throws Exception {
        var dto = new AddTransportDTO(
                "BUS",
                "INWARD",
                "2024-11-01T11:00:00",
                new AddTransportPlaceDTO(
                        "Victoria Coach Station, 164 Buckingham Palace Rd",
                        "London",
                        "SW1W 9TP",
                        "United Kingdom"),
                new AddTransportPriceDTO(
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

        Assert.assertNotNull(transport);
        Assert.assertEquals(TransportType.BUS, transport.getType());
        Assert.assertEquals(Journey.INWARD, transport.getJourney());
        Assert.assertEquals(LocalDateTime.parse("2024-11-01T11:00:00"), transport.getDate());
        Assert.assertEquals("Victoria Coach Station, 164 Buckingham Palace Rd", transport.getPlace().getAddress());
        Assert.assertEquals("London", transport.getPlace().getCity());
        Assert.assertEquals("SW1W 9TP", transport.getPlace().getZipCode());
        Assert.assertEquals("United Kingdom", transport.getPlace().getCountry());
        Assert.assertEquals(Currency.GBP, transport.getPrice().getCurrency());
        Assert.assertEquals(0, BigDecimal.valueOf(34.0).compareTo(transport.getPrice().getAmount()));
    }

    @Test
    void whenTripDoesNotExist_ShouldSendNotFound() throws Exception {
        var dto = new AddTransportDTO(
                "BUS",
                "INWARD",
                "2024-11-01T11:00:00",
                new AddTransportPlaceDTO(
                        "Victoria Coach Station, 164 Buckingham Palace Rd",
                        "London",
                        "SW1W 9TP",
                        "United Kingdom"),
                new AddTransportPriceDTO(
                        "GBP", 34));

        var response = mockMvc
                .perform(MockMvcRequestBuilders.post("/trips/non-existing-trip-id/add-transport")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }
}
