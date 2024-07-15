package com.api.planner.service;

import com.api.planner.dtos.request.ActivitieRequestDto;
import com.api.planner.dtos.response.ActivityDetailsDto;
import com.api.planner.dtos.response.ActivityResponseDto;
import com.api.planner.entity.activity.Activity;
import com.api.planner.entity.trip.Trip;
import com.api.planner.repository.ActivitieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {
    @Autowired
    private ActivitieRepository activitieRepository;

    public ActivityResponseDto registerActivity(ActivitieRequestDto request, Trip trip){
        Activity activity = new Activity(request, trip);
        this.activitieRepository.save(activity);
        return new ActivityResponseDto(activity.getId());
    }

    public List<ActivityDetailsDto> getAllActivitiesFromId(UUID id){
        return this.activitieRepository.findByTripId(id).stream()
                .map(activity -> new ActivityDetailsDto(activity.getId(), activity.getTitle(), activity.getOccursAt())).toList();
    }
}
