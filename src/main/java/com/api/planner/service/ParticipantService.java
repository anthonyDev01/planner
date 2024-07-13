package com.api.planner.service;


import com.api.planner.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipantService {
    private final TripRepository tripRepository;


    public void registerParticipantsToEvent(List<String> participantsToInvite, UUID id){

    }

    public void triggerConfirmationEmailToParticipants(UUID tripId){}
}
