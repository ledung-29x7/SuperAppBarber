package com.example.SuperAppBarber.salon.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SuperAppBarber.salon.model.SalonEntity;

@Repository
public interface SalonRepository extends JpaRepository<SalonEntity, UUID> {

    // Optional<SalonEntity> findByOwnerId(UUID ownerId);

    SalonEntity findBySalonId(UUID salonId);

    Optional<SalonEntity> findByOwnerIdAndSalonId(UUID ownerId, UUID salonId);

    List<SalonEntity> findAllByOwnerId(UUID ownerId);

    List<SalonEntity> findAll();

}
