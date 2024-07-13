package com.api.planner.controller;

import com.api.planner.dtos.request.TripRequestDto;
import com.api.planner.dtos.response.TripResponseDto;
import com.api.planner.entity.trip.Trip;
import com.api.planner.service.ParticipantService;
import com.api.planner.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {
    private final TripService tripService;
    private final ParticipantService participantService;
    @PostMapping
    public ResponseEntity<TripResponseDto> createTrip(@RequestBody TripRequestDto body){
        Trip trip = new Trip(body);
        this.participantService.registerParticipantsToEvent(body.emails_to_invite(), trip.getId());
        this.tripService.registerTrip(trip);
        return ResponseEntity.ok().body(new TripResponseDto(trip.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id){
        return ResponseEntity.ok().body(this.tripService.getTripDetailsById(id));
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<TripResponseDto> confirmTrip(@PathVariable UUID id){
        Trip trip = this.tripService.confirmTrip(id);
        return ResponseEntity.ok().body(new TripResponseDto(trip.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripResponseDto> updateTrip(@PathVariable UUID id, @RequestBody TripRequestDto request){
        Trip trip = this.tripService.updateTrip(id, request);
        this.participantService.triggerConfirmationEmailToParticipants(id);
        return ResponseEntity.ok().body(new TripResponseDto(trip.getId()));
    }
}
