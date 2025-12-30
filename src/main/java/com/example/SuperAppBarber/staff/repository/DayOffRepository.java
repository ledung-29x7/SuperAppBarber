package com.example.SuperAppBarber.staff.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SuperAppBarber.staff.model.DayOffEntity;

public interface DayOffRepository extends JpaRepository<DayOffEntity, Long> {

    List<DayOffEntity> findBySalonId(UUID salonId);

    List<DayOffEntity> findByStaffId(UUID staffId);

    List<DayOffEntity> findByStaffIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            UUID staffId,
            LocalDate date1,
            LocalDate date2);
}
