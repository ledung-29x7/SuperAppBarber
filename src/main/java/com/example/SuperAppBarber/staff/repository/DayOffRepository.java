package com.example.SuperAppBarber.staff.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.SuperAppBarber.common.enums.DayOffStatus;
import com.example.SuperAppBarber.staff.model.DayOffEntity;

public interface DayOffRepository extends JpaRepository<DayOffEntity, Long>, JpaSpecificationExecutor<DayOffEntity> {

    List<DayOffEntity> findBySalonId(UUID salonId);

    List<DayOffEntity> findByStaffId(UUID staffId);

    List<DayOffEntity> findByStaffIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            UUID staffId,
            LocalDate date1,
            LocalDate date2);

    List<DayOffEntity> findByStaffIdAndDeletedAtIsNull(UUID staffId);

    // @Query("""
    // SELECT d
    // FROM DayOffEntity d
    // WHERE d.salonId = :salonId
    // AND d.deletedAt IS NULL
    // AND (:status IS NULL OR d.status = :status)
    // AND (:staffId IS NULL OR d.staffId = :staffId)
    // AND (
    // :fromDate IS NULL OR d.endDate >= :fromDate
    // )
    // AND (
    // :toDate IS NULL OR d.startDate <= :toDate
    // )
    // ORDER BY d.startDate ASC
    // """)
    // List<DayOffEntity> searchDayOff(
    // @Param("salonId") UUID salonId,
    // @Param("fromDate") LocalDate fromDate,
    // @Param("toDate") LocalDate toDate,
    // @Param("status") DayOffStatus status,
    // @Param("staffId") UUID staffId);
}
