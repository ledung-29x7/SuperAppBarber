package com.example.SuperAppBarber.servicecatalog.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.SuperAppBarber.servicecatalog.model.ServiceEntity;
import com.example.SuperAppBarber.servicecatalog.model.StaffServiceEntity;
import com.example.SuperAppBarber.servicecatalog.model.StaffServiceId;

public interface StaffServiceRepository
                extends JpaRepository<StaffServiceEntity, StaffServiceId> {

        List<StaffServiceEntity> findByIdStaffIdAndDeletedAtIsNull(UUID staffId);

        boolean existsByIdAndDeletedAtIsNull(StaffServiceId id);

        @Modifying
        @Query("""
                            UPDATE StaffServiceEntity s
                            SET s.deletedAt = CURRENT_TIMESTAMP
                            WHERE s.id.staffId = :staffId
                              AND s.id.serviceId = :serviceId
                              AND s.deletedAt IS NULL
                        """)
        int softDelete(@Param("staffId") UUID staffId,
                        @Param("serviceId") Long serviceId);

        @Query("""
                            SELECT s
                            FROM ServiceEntity s
                            JOIN StaffServiceEntity ss
                              ON s.id = ss.id.serviceId
                            WHERE ss.id.staffId = :staffId
                              AND s.active = true
                        """)
        List<ServiceEntity> findServicesByStaffId(@Param("staffId") UUID staffId);

}
