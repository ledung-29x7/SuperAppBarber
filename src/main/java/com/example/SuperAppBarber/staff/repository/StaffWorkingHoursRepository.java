package com.example.SuperAppBarber.staff.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.SuperAppBarber.staff.model.StaffWorkingHoursEntity;

import jakarta.transaction.Transactional;

public interface StaffWorkingHoursRepository extends JpaRepository<StaffWorkingHoursEntity, Long> {

    List<StaffWorkingHoursEntity> findByStaffId(UUID staffId);

    List<StaffWorkingHoursEntity> findByStaffIdAndDeletedAtIsNull(UUID staffId);

    @Modifying
    @Transactional
    @Query("""
                UPDATE StaffWorkingHoursEntity s
                SET s.deletedAt = CURRENT_TIMESTAMP
                WHERE s.staffId = :staffId
                AND s.deletedAt IS NULL
            """)
    int softDeleteByStaffId(@Param("staffId") UUID staffId);

}
