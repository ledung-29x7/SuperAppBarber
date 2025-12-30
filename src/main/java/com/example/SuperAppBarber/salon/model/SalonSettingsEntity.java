package com.example.SuperAppBarber.salon.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "salon_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalonSettingsEntity {

    @Id
    @Column(name = "salon_id")
    private UUID salonId;

    @Column(name = "open_time")
    private LocalTime openTime;

    @Column(name = "close_time")
    private LocalTime closeTime;

    @Column(name = "booking_interval")
    private Integer bookingInterval;

    @Column(name = "allow_auto_booking")
    private Boolean allowAutoBooking;

    @Column(name = "cancel_before_minutes")
    private Integer cancelBeforeMinutes;
}