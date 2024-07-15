package com.api.planner.repository;

import com.api.planner.entity.activity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ActivitieRepository extends JpaRepository<Activity, UUID> {
    public List<Activity> findByTripId(UUID id);
}
