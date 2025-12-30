package com.example.SuperAppBarber.review.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(name = "salon_id")
    private UUID salonId;

    @Column(name = "staff_id")
    private UUID staffId;

    @Column(name = "customer_id")
    private UUID customerId;

    private Integer rating;

    private String comment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}