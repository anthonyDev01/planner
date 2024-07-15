package com.api.planner.entity.link;

import com.api.planner.dtos.request.LinkRequestDto;
import com.api.planner.entity.trip.Trip;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "links")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;

    private String url;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    public Link(LinkRequestDto request, Trip trip){
        this.title = request.title();
        this.url = request.url();
        this.trip = trip;
    }
}
