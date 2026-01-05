package com.example.SuperAppBarber.promotion.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.SuperAppBarber.promotion.model.PromotionEntity;

public interface PromotionRepository extends JpaRepository<PromotionEntity, Long> {

    List<PromotionEntity> findBySalonIdAndActiveTrue(UUID salonId);

    List<PromotionEntity> findBySalonIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            UUID salonId,
            LocalDate now1,
            LocalDate now2);

    @Query("""
                SELECT p FROM PromotionEntity p
                WHERE p.salonId = :salonId
                  AND p.active = true
                  AND p.deletedAt IS NULL
                  AND (:date IS NULL OR
                      (p.startDate IS NULL OR p.startDate <= :date)
                      AND (p.endDate IS NULL OR p.endDate >= :date)
                  )
            """)
    List<PromotionEntity> findActivePromotions(
            UUID salonId,
            LocalDate date);
}
