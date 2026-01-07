package com.example.SuperAppBarber.customer.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SuperAppBarber.customer.model.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

    List<CustomerEntity> findBySalonId(UUID salonId);

    Optional<CustomerEntity> findBySalonIdAndPhone(UUID salonId, String phone);

    List<CustomerEntity> findBySalonIdAndNameContainingIgnoreCase(UUID salonId, String keyword);

    Optional<CustomerEntity> findByUserId(UUID userId);

}
