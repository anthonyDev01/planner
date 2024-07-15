package com.api.planner.dtos.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record LinkDetailsResponseDto (
            UUID id,
            String title,
            String url

){
}
