package com.example.SuperAppBarber.booking.dto.sdi;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RescheduleBookingRequest {

    @NotNull
    private LocalDateTime startTime;
}
