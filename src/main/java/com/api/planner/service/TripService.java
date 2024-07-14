package com.api.planner.service;

import com.api.planner.dtos.request.TripRequestDto;
import com.api.planner.entity.trip.Trip;
import com.api.planner.entity.trip.exception.TripNotFoundExeception;
import com.api.planner.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TripService {
    private final TripRepository tripRepository;
    public Trip registerTrip(Trip trip){
        return this.tripRepository.save(trip);
    }

    public Trip getTripDetailsById(UUID id){
        return this.tripRepository.findById(id).orElseThrow(() -> new TripNotFoundExeception("Trip Not Found"));
    }

    public Trip updateTrip(UUID id, TripRequestDto request){
        Trip trip = this.tripRepository.findById(id).orElseThrow(() -> new TripNotFoundExeception("Trip Not Found"));
        trip.setDestination(request.destination());
        trip.setStartsAt( LocalDateTime.parse(request.starts_at(), DateTimeFormatter.ISO_DATE_TIME));
        trip.setEndsAt(LocalDateTime.parse(request.ends_at(), DateTimeFormatter.ISO_DATE_TIME));
        return this.tripRepository.save(trip);
    }

    public Trip confirmTrip(UUID id){
        Trip trip = this.tripRepository.findById(id).orElseThrow(() -> new TripNotFoundExeception("Trip Not Found"));
        trip.setIsConfirmed(true);
        return this.tripRepository.save(trip);
    }


}
