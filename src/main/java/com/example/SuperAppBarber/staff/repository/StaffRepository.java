package com.example.SuperAppBarber.staff.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.SuperAppBarber.staff.model.StaffEntity;

public interface StaffRepository extends JpaRepository<StaffEntity, UUID> {

  List<StaffEntity> findAllBySalonId(UUID salonId);

  List<StaffEntity> findBySalonIdAndActiveTrue(UUID salonId);

  Optional<StaffEntity> findByUserIdAndSalonId(UUID userId, UUID salonId);

  boolean existsBySalonIdAndUserId(UUID salonId, UUID userId);

  boolean existsByStaffIdAndSalonId(UUID staffId, UUID salonId);

  Optional<StaffEntity> findByUserId(UUID userId);

  // @Query(value = "select staff_id from staff where user_id = :userId ",
  // nativeQuery = true)
  // Optional<UUID> findStaffIdByUserId(UUID userId);

  @Query(value = "SELECT staff_id FROM staff WHERE user_id = :userId", nativeQuery = true)
  Optional<UUID> findStaffIdByUserId(@Param("userId") UUID userId);

  // Booking
  @Query("""
          SELECT s.staffId
          FROM StaffEntity s
          JOIN StaffServiceEntity ss
            ON s.staffId = ss.id.staffId
          WHERE s.salonId = :salonId
            AND ss.id.serviceId = :serviceId
            AND s.active = true
          ORDER BY (
              SELECT COUNT(b)
              FROM BookingEntity b
              WHERE b.staffId = s.staffId
                AND b.startTime >= :dayStart
                AND b.startTime < :dayEnd
          ) ASC
      """)
  List<UUID> findAvailableStaffForService(
      @Param("salonId") UUID salonId,
      @Param("serviceId") Long serviceId,
      @Param("dayStart") LocalDateTime dayStart,
      @Param("dayEnd") LocalDateTime dayEnd);

  @Query("""
          SELECT s.staffId
          FROM StaffEntity s
          JOIN StaffServiceEntity ss
            ON s.staffId = ss.id.staffId
          WHERE s.salonId = :salonId
            AND ss.id.serviceId = :serviceId
            AND s.active = true
      """)
  List<UUID> findStaffIdsBySalonAndService(
      @Param("salonId") UUID salonId,
      @Param("serviceId") Long serviceId);

}
