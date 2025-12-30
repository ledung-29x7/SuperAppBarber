package com.example.SuperAppBarber.review.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SuperAppBarber.review.model.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findBySalonId(UUID salonId);

    List<ReviewEntity> findByStaffId(UUID staffId);
}