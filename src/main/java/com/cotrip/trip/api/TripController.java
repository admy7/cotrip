package com.cotrip.trip.api;

import an.awesome.pipelinr.Pipeline;
import com.cotrip.trip.api.dtos.CreateTripDTO;
import com.cotrip.trip.application.usecases.CreateTripCommand;
import com.cotrip.trip.domain.viewmodels.IdResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trips")
public class TripController {
    private Pipeline pipeline;

    public TripController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @PostMapping
    public ResponseEntity<IdResponse> createTrip(@Valid @RequestBody CreateTripDTO dto) {
        var result = pipeline.send(new CreateTripCommand(
                dto.getOrigin(),
                dto.getDestination(),
                dto.getStartDate(),
                dto.getEndDate()
        ));

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
