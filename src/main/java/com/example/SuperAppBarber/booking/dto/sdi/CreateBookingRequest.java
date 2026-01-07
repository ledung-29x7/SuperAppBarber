package com.example.SuperAppBarber.booking.dto.sdi;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookingRequest {

    @NotNull
    private UUID salonId;

    @NotNull
    private Long serviceId;

    private UUID staffId; // null = auto assign

    @NotNull
    private LocalDateTime startTime;

    private String customerName;
    private String customerPhone;
}
