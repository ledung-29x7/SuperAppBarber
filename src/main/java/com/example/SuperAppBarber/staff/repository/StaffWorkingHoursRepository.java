package com.example.SuperAppBarber.staff.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SuperAppBarber.staff.model.StaffWorkingHoursEntity;

public interface StaffWorkingHoursRepository extends JpaRepository<StaffWorkingHoursEntity, Long> {

    List<StaffWorkingHoursEntity> findByStaffId(UUID staffId);

    List<StaffWorkingHoursEntity> findByStaffIdAndDeletedAtIsNull(UUID staffId);
}
