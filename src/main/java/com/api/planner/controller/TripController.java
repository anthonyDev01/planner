package com.api.planner.controller;
import com.api.planner.dtos.request.ActivitieRequestDto;
import com.api.planner.dtos.request.LinkRequestDto;
import com.api.planner.dtos.request.ParticipantRequestDto;
import com.api.planner.dtos.request.TripRequestDto;
import com.api.planner.dtos.response.*;
import com.api.planner.entity.trip.Trip;
import com.api.planner.service.ActivityService;
import com.api.planner.service.LinkService;
import com.api.planner.service.ParticipantService;
import com.api.planner.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {
    private final TripService tripService;
    private final ParticipantService participantService;
    private final ActivityService activityService;
    private final LinkService linkService;
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

    @PostMapping("/{id}/activitie")
    public ResponseEntity<ActivityResponseDto> registerActivitie(@PathVariable UUID id, @RequestBody ActivitieRequestDto body){
        Trip trip = this.tripService.getTripDetailsById(id);
        ActivityResponseDto activityResponseDto = this.activityService.registerActivity(body, trip);
        return ResponseEntity.ok().body(activityResponseDto);
    }

    @PostMapping("/{id}/links")
    public ResponseEntity<LinkResponseDto> registerLink(@PathVariable UUID id, @RequestBody LinkRequestDto body){
        Trip trip = tripService.getTripDetailsById(id);
        LinkResponseDto link = this.linkService.registerLink(body, trip);
        return ResponseEntity.ok().body(link);
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

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityDetailsDto>> getAllActivities(@PathVariable UUID id){
        List<ActivityDetailsDto> activityDetails = this.activityService.getAllActivitiesFromId(id);
        return ResponseEntity.ok().body(activityDetails);
    }

    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkDetailsResponseDto>> getAllLinks(@PathVariable UUID id){
        List<LinkDetailsResponseDto> links = this.linkService.getAllLinksFromTripId(id);
        return ResponseEntity.ok().body(links);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripResponseDto> updateTrip(@PathVariable UUID id, @RequestBody TripRequestDto request){
        Trip trip = this.tripService.updateTrip(id, request);
        this.participantService.triggerConfirmationEmailToParticipants(id);
        return ResponseEntity.ok().body(new TripResponseDto(trip.getId()));
    }
}
