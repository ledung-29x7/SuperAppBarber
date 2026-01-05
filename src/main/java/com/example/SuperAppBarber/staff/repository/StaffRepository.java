package com.example.SuperAppBarber.staff.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SuperAppBarber.staff.model.StaffEntity;

public interface StaffRepository extends JpaRepository<StaffEntity, UUID> {

    List<StaffEntity> findAllBySalonId(UUID salonId);

    List<StaffEntity> findBySalonIdAndActiveTrue(UUID salonId);

    Optional<StaffEntity> findByUserIdAndSalonId(UUID userId, UUID salonId);

    boolean existsBySalonIdAndUserId(UUID salonId, UUID userId);

    boolean existsByStaffIdAndSalonId(UUID staffId, UUID salonId);

    Optional<StaffEntity> findByUserId(UUID userId);
}
