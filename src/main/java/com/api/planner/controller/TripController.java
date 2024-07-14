package com.api.planner.controller;

import com.api.planner.dtos.request.ParticipantRequestDto;
import com.api.planner.dtos.request.TripRequestDto;
import com.api.planner.dtos.response.ParticipantDataResponseDto;
import com.api.planner.dtos.response.PaticipantCreateResponseDto;
import com.api.planner.dtos.response.TripResponseDto;
import com.api.planner.entity.participant.Participant;
import com.api.planner.entity.trip.Trip;
import com.api.planner.service.ParticipantService;
import com.api.planner.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
        this.tripService.registerTrip(trip);
        this.participantService.registerParticipantsToEvent(body.emails_to_invite(), trip);
        return ResponseEntity.ok().body(new TripResponseDto(trip.getId()));
    }

    @PostMapping("/{id}/invate")
    public ResponseEntity<PaticipantCreateResponseDto> inveteParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestDto body){
        Trip trip = this.tripService.confirmTrip(id);

        PaticipantCreateResponseDto paticipantResponse = this.participantService.registerParticipantToEvent(body.email(), trip);

        if (trip.getIsConfirmed()){
            this.participantService.triggerConfirmationEmailToParticipant(body.email());
        }

        return ResponseEntity.ok().body(paticipantResponse);
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

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantDataResponseDto>> getAllParticipants(@PathVariable UUID id){
        List<ParticipantDataResponseDto> participantList = this.participantService.getAllParticipantsFromTrip(id);
        return ResponseEntity.ok().body(participantList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripResponseDto> updateTrip(@PathVariable UUID id, @RequestBody TripRequestDto request){
        Trip trip = this.tripService.updateTrip(id, request);
        this.participantService.triggerConfirmationEmailToParticipants(id);
        return ResponseEntity.ok().body(new TripResponseDto(trip.getId()));
    }
}
