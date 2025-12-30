package com.example.SuperAppBarber.payment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SuperAppBarber.payment.model.PaymentEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    List<PaymentEntity> findBySubscriptionId(Long subscriptionId);
}
