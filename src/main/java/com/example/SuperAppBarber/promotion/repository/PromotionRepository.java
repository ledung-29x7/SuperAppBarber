package com.example.SuperAppBarber.promotion.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SuperAppBarber.promotion.model.PromotionEntity;

public interface PromotionRepository extends JpaRepository<PromotionEntity, Long> {

    List<PromotionEntity> findBySalonIdAndActiveTrue(UUID salonId);

    List<PromotionEntity> findBySalonIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            UUID salonId,
            LocalDate now1,
            LocalDate now2);
}
