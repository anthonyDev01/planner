package com.api.planner.dtos.response;

import java.util.UUID;

public record ParticipantDataResponseDto(
        UUID id,
        String name,
        String email,
        Boolean isConfirmed
) {
}
