package com.example.SuperAppBarber.booking.dto.sdo;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AvailabilityResponse {

    private UUID staffId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
