package com.api.planner.entity.activity;

import com.api.planner.dtos.request.ActivitieRequestDto;
import com.api.planner.entity.trip.Trip;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "activities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;

    @Column(name = "occurs_at", nullable = false)
    private LocalDateTime occursAt;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    public Activity(ActivitieRequestDto request, Trip trip){
        this.title = request.title();
        this.occursAt = LocalDateTime.parse(request.occurs_at(), DateTimeFormatter.ISO_DATE_TIME);
        this.trip = trip;
    }


}
