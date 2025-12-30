package com.example.SuperAppBarber.booking.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "booking_services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(BookingServiceId.class)
public class BookingServiceEntity {

    @Id
    @Column(name = "booking_id")
    private UUID bookingId;

    @Id
    @Column(name = "service_id")
    private Long serviceId;
}
