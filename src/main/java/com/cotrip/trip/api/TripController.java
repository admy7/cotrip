package com.cotrip.trip.api;

import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Voidy;
import com.cotrip.trip.api.dtos.AddAccommodationDTO;
import com.cotrip.trip.api.dtos.AddTransportDTO;
import com.cotrip.trip.api.dtos.CreateTripDTO;
import com.cotrip.trip.application.usecases.AddAccommodationCommand;
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
  private final Pipeline pipeline;

  public TripController(Pipeline pipeline) {
    this.pipeline = pipeline;
  }

  @PostMapping
  public ResponseEntity<IdResponse> createTrip(@Valid @RequestBody CreateTripDTO dto) {
    var result =
        pipeline.send(
            new CreateTripCommand(dto.origin(), dto.destination(), dto.startDate(), dto.endDate()));

    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

  @PostMapping("{tripId}/add-transport")
  public ResponseEntity<Voidy> addTransport(
      @PathVariable String tripId, @Valid @RequestBody AddTransportDTO dto) {
    pipeline.send(
        new AddTransportCommand(
            tripId,
            dto.type(),
            dto.journey(),
            dto.date(),
            dto.place().address(),
            dto.place().city(),
            dto.place().zipCode(),
            dto.place().country(),
            dto.price().amount(),
            dto.price().currency()));

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping("/{tripId}/add-accommodation")
  public ResponseEntity<Voidy> addAccommodation(
      @PathVariable String tripId, @Valid @RequestBody AddAccommodationDTO dto) {
    pipeline.send(
        new AddAccommodationCommand(
            tripId,
            dto.name(),
            dto.place().address(),
            dto.place().city(),
            dto.place().zipCode(),
            dto.place().country(),
            dto.startDate(),
            dto.endDate(),
            dto.price().amount(),
            dto.price().currency()));

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
