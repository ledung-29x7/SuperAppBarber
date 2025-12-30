package com.example.SuperAppBarber.booking.model;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingServiceId implements Serializable {
    private UUID bookingId;
    private Long serviceId;
}
