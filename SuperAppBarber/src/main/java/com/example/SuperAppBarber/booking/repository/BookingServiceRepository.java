package com.example.SuperAppBarber.booking.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SuperAppBarber.booking.model.BookingServiceEntity;
import com.example.SuperAppBarber.booking.model.BookingServiceId;

public interface BookingServiceRepository
        extends JpaRepository<BookingServiceEntity, BookingServiceId> {

    List<BookingServiceEntity> findByBookingId(UUID bookingId);

    List<BookingServiceEntity> findByServiceId(Long serviceId);
}
