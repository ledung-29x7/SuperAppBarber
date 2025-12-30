package com.example.SuperAppBarber.salon.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SuperAppBarber.salon.model.SalonEntity;

public interface SalonRepository extends JpaRepository<SalonEntity, UUID> {

    List<SalonEntity> findByOwnerId(UUID ownerId);

}
