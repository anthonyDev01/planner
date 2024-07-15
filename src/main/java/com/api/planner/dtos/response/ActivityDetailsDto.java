package com.api.planner.dtos.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityDetailsDto(
        UUID id,
        String title,
        LocalDateTime occurs_at
) {
}
