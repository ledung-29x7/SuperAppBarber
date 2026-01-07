package com.example.SuperAppBarber.booking.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.SuperAppBarber.booking.model.BookingEntity;

import jakarta.persistence.LockModeType;

public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {

  // Lock tất cả booking của staff trong ngày
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("""
          SELECT b FROM BookingEntity b
          WHERE b.staffId = :staffId
            AND b.startTime >= :dayStart
            AND b.startTime < :dayEnd
      """)
  List<BookingEntity> lockStaffBookings(
      @Param("staffId") UUID staffId,
      @Param("dayStart") LocalDateTime dayStart,
      @Param("dayEnd") LocalDateTime dayEnd);

  // Check conflict
  @Query("""
          SELECT COUNT(b) > 0
          FROM BookingEntity b
          WHERE b.staffId = :staffId
            AND b.status IN ('PENDING', 'CONFIRMED')
            AND (
                 b.startTime < :endTime
             AND b.endTime > :startTime
            )
      """)
  boolean existsConflict(
      @Param("staffId") UUID staffId,
      @Param("startTime") LocalDateTime startTime,
      @Param("endTime") LocalDateTime endTime);

  boolean existsByBookingCode(String bookingCode);

  Optional<BookingEntity> findByBookingCode(String bookingCode);

  @Query("""
          SELECT b FROM BookingEntity b
          WHERE b.staffId = :staffId
            AND b.status IN ('PENDING', 'CONFIRMED')
            AND b.startTime >= :dayStart
            AND b.startTime < :dayEnd
      """)
  List<BookingEntity> findBookingsOfStaff(
      @Param("staffId") UUID staffId,
      @Param("dayStart") LocalDateTime dayStart,
      @Param("dayEnd") LocalDateTime dayEnd);

  // List<BookingEntity> findBySalonId(UUID salonId);

  // List<BookingEntity> findByStaffId(UUID staffId);

  // List<BookingEntity> findBySalonIdAndStartTimeBetween(
  // UUID salonId,
  // LocalDateTime from,
  // LocalDateTime to);

  // // 🔥 CHECK TRÙNG LỊCH
  // @Query("""
  // SELECT b FROM BookingEntity b
  // WHERE b.staffId = :staffId
  // AND b.status NOT IN ('CANCELLED', 'NO_SHOW')
  // AND (
  // (:start < b.endTime AND :end > b.startTime)
  // )
  // """)
  // List<BookingEntity> findConflictedBookings(
  // UUID staffId,
  // LocalDateTime start,
  // LocalDateTime end);

  // long countBySalonIdAndStatus(UUID salonId, BookingStatus status);

}
