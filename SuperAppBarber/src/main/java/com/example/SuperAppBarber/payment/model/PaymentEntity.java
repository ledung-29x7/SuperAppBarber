package com.example.SuperAppBarber.payment.model;

import java.time.LocalDateTime;

import com.example.SuperAppBarber.common.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "subscription_id")
    private Long subscriptionId;

    private Double amount;

    private String method;
    private String provider;

    @Column(name = "transaction_id")
    private String transactionId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime paidAt;
}
