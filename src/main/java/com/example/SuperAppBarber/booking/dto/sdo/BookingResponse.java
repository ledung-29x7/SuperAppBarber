package com.example.SuperAppBarber.booking.dto.sdo;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.SuperAppBarber.common.enums.BookingStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookingResponse {

    private UUID bookingId;
    private String bookingCode;
    private UUID salonId;
    private UUID staffId;
    private UUID customerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BookingStatus status;
}
