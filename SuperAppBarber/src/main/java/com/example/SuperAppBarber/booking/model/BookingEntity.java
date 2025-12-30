package com.example.SuperAppBarber.booking.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.SuperAppBarber.common.base.BaseEntity;
import com.example.SuperAppBarber.common.enums.BookingStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingEntity extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "booking_id")
    private UUID bookingId;

    @Column(name = "salon_id")
    private UUID salonId;

    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "staff_id")
    private UUID staffId;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private String note;
}
