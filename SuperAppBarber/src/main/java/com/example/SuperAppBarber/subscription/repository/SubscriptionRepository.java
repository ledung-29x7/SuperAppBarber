package com.example.SuperAppBarber.subscription.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SuperAppBarber.common.enums.SubscriptionStatus;
import com.example.SuperAppBarber.subscription.model.SubscriptionEntity;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

    Optional<SubscriptionEntity> findBySalonIdAndStatus(
            UUID salonId,
            SubscriptionStatus status);
}
