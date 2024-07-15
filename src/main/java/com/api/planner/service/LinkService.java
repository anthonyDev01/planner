package com.api.planner.service;

import com.api.planner.dtos.request.LinkRequestDto;
import com.api.planner.dtos.response.LinkDetailsResponseDto;
import com.api.planner.dtos.response.LinkResponseDto;
import com.api.planner.entity.link.Link;
import com.api.planner.entity.trip.Trip;
import com.api.planner.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {
    @Autowired
    private LinkRepository linkRepository;

    public LinkResponseDto registerLink(LinkRequestDto request, Trip trip){
        Link link = new Link(request, trip);
        this.linkRepository.save(link);
        return new LinkResponseDto(link.getId());
    }

    public List<LinkDetailsResponseDto> getAllLinksFromTripId(UUID id){
        return this.linkRepository.findByTripId(id).stream()
                .map(link -> new LinkDetailsResponseDto(link.getId(), link.getTitle(), link.getUrl())).toList();

    }
}
