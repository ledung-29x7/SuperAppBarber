package com.example.SuperAppBarber.staff.repository;

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

}
