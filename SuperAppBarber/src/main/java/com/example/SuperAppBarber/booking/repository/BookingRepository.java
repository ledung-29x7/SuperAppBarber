package com.example.SuperAppBarber.booking.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.SuperAppBarber.booking.model.BookingEntity;
import com.example.SuperAppBarber.common.enums.BookingStatus;

public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {

    List<BookingEntity> findBySalonId(UUID salonId);

    List<BookingEntity> findByStaffId(UUID staffId);

    List<BookingEntity> findBySalonIdAndStartTimeBetween(
            UUID salonId,
            LocalDateTime from,
            LocalDateTime to);

    // 🔥 CHECK TRÙNG LỊCH
    @Query("""
                SELECT b FROM BookingEntity b
                WHERE b.staffId = :staffId
                  AND b.status NOT IN ('CANCELLED', 'NO_SHOW')
                  AND (
                      (:start < b.endTime AND :end > b.startTime)
                  )
            """)
    List<BookingEntity> findConflictedBookings(
            UUID staffId,
            LocalDateTime start,
            LocalDateTime end);

    long countBySalonIdAndStatus(UUID salonId, BookingStatus status);
}
