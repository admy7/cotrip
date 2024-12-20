package com.cotrip.trip.api;

import com.cotrip.CotripBackendApplication;
import com.cotrip.trip.api.dtos.AddAccommodationDTO;
import com.cotrip.trip.api.dtos.AddPlaceDTO;
import com.cotrip.trip.api.dtos.AddPriceDTO;
import com.cotrip.trip.application.ports.TripRepository;
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

@SpringBootTest
@AutoConfigureMockMvc
@Import(CotripBackendApplication.class)
public class AddAccommodationCommandE2ETest {

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
    void shouldAddAccommodationToTrip() throws Exception {
        var dto = new AddAccommodationDTO(
                "The Emory",
                new AddPlaceDTO(
                        "Old Barrack Yard",
                        "London",
                        "SW1X 7NP",
                        "United Kingdom"),
                "2025-01-01T08:00:00",
                "2025-01-03T08:00:00",
                new AddPriceDTO(
                        "EUR",
                        4400));

        mockMvc
                .perform(MockMvcRequestBuilders.post(String.format("/trips/%s/add-accommodation", tripId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();

        var tripWithAccommodation = tripRepository.findById(tripId).get();

        var accommodation = tripWithAccommodation.getAccommodations().get(0);

        Assertions.assertNotNull(accommodation);
    }
}