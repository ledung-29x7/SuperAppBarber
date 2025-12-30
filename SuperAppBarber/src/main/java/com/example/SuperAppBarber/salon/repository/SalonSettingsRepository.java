package com.example.SuperAppBarber.salon.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SuperAppBarber.salon.model.SalonSettingsEntity;

public interface SalonSettingsRepository extends JpaRepository<SalonSettingsEntity, UUID> {
}
