package com.api.planner.service;


import com.api.planner.dtos.request.ParticipantRequestDto;
import com.api.planner.dtos.response.ParticipantDataResponseDto;
import com.api.planner.dtos.response.PaticipantCreateResponseDto;
import com.api.planner.entity.participant.Participant;
import com.api.planner.entity.trip.Trip;
import com.api.planner.repository.ParticipantRepository;
import com.api.planner.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipantService {
    private final ParticipantRepository participantRepository;

    public void registerParticipantsToEvent(List<String> participantsToInvite, Trip trip){
        List<Participant> participants = participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();
        this.participantRepository.saveAll(participants);
        System.out.println(participants.get(0).getId());
    }

    public PaticipantCreateResponseDto registerParticipantToEvent(String email, Trip trip){
        Participant newParticipant = new Participant(email, trip);
        this.participantRepository.save(newParticipant);
        return new PaticipantCreateResponseDto(newParticipant.getId());
    }

    public void triggerConfirmationEmailToParticipant(String email){

    }

    public void triggerConfirmationEmailToParticipants(UUID tripId){}

    public Participant confirmParticipant(UUID id, ParticipantRequestDto request){
        Participant participant = this.participantRepository.findById(id).orElseThrow(() -> new RuntimeException("Participant not found"));
        participant.setIsConfirmed(true);
        participant.setName(request.name());
        return this.participantRepository.save(participant);
    }

    public List<ParticipantDataResponseDto> getAllParticipantsFromTrip(UUID id){
        return this.participantRepository.findByTripId(id).stream()
                .map(participant -> new ParticipantDataResponseDto(participant.getId(), participant.getName(), participant.getEmail(), participant.getIsConfirmed())).toList();
    }
}
