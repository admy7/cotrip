package com.cotrip.trip.api;

import com.cotrip.CotripBackendApplication;
import com.cotrip.core.api.ErrorResponse;
import com.cotrip.trip.api.dtos.CreateTripDTO;
import com.cotrip.trip.application.ports.TripRepository;
import com.cotrip.trip.domain.viewmodels.IdResponse;
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

@SpringBootTest
@AutoConfigureMockMvc
@Import(CotripBackendApplication.class)
public class CreateTripCommandE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TripRepository tripRepository;

    @BeforeEach
    void setUp() {
        tripRepository.deleteAll();
    }

    @Test
    void shouldCreateTrip() throws Exception {
        var dto = new CreateTripDTO(
                "Paris",
                "London",
                "2024-12-03",
                "2024-12-08"
        );

        var response = mockMvc
                .perform(MockMvcRequestBuilders.post("/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var idResponse = objectMapper.readValue(response.getResponse().getContentAsString(), IdResponse.class);

        Assert.assertNotNull(idResponse);

        var foundTrip = tripRepository.findById(idResponse.getId()).get();

        Assert.assertEquals(foundTrip.getOrigin(), dto.origin());
        Assert.assertEquals(foundTrip.getDestination(), dto.destination());
        Assert.assertEquals(foundTrip.getStartDate().toString(), dto.startDate());
        Assert.assertEquals(foundTrip.getEndDate().toString(), dto.endDate());
    }

    @Test
    void whenDatesAreInvalid_ShouldSendBadRequest() throws Exception {
        var dto = new CreateTripDTO(
                "Paris",
                "London",
                "2024-15-03",
                "2024-18-03"
        );

        var response = mockMvc
                .perform(MockMvcRequestBuilders.post("/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        var errorResponse = objectMapper.readValue(response.getResponse().getContentAsString(), ErrorResponse.class);

        Assert.assertEquals(400, errorResponse.getStatus());
        Assert.assertEquals("Trip: Invalid date format. Format must be YYYY-MM-DD.", errorResponse.getMessage());
    }
}
