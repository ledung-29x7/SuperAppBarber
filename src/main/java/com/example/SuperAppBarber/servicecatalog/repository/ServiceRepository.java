package com.example.SuperAppBarber.servicecatalog.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SuperAppBarber.servicecatalog.model.ServiceEntity;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {

    List<ServiceEntity> findBySalonId(UUID salonId);

    List<ServiceEntity> findBySalonIdAndActiveTrue(UUID salonId);

    List<ServiceEntity> findBySalonIdAndDeletedAtIsNull(UUID salonId);

    Optional<ServiceEntity> findByServiceIdAndSalonIdAndDeletedAtIsNull(
            Long serviceId, UUID salonId);
}
