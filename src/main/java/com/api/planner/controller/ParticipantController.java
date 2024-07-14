package com.api.planner.controller;


import com.api.planner.dtos.request.ParticipantRequestDto;
import com.api.planner.entity.participant.Participant;
import com.api.planner.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/participant")
public class ParticipantController {
    @Autowired
    private ParticipantService participantService;
    @PostMapping("/{id}/confirm")
    public ResponseEntity<Participant> confirmParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestDto body){
        Participant participant = this.participantService.confirmParticipant(id, body);
        return ResponseEntity.ok().body(participant);
    }
}
