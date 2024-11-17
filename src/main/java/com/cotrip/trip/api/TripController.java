package com.cotrip.trip.api;

import an.awesome.pipelinr.Pipeline;
import com.cotrip.trip.api.dtos.AddTransportDTO;
import com.cotrip.trip.api.dtos.CreateTripDTO;
import com.cotrip.trip.application.usecases.AddTransportCommand;
import com.cotrip.trip.application.usecases.CreateTripCommand;
import com.cotrip.trip.domain.viewmodels.IdResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                dto.origin(),
                dto.destination(),
                dto.startDate(),
                dto.endDate()
        ));

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("{tripId}/add-transport")
    public ResponseEntity addTransport(@PathVariable String tripId,
                                       @Valid @RequestBody AddTransportDTO dto) {
        pipeline.send(new AddTransportCommand(
                tripId,
                dto.type(),
                dto.journey(),
                dto.date(),
                dto.place(),
                dto.price()));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
