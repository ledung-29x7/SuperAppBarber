package com.example.SuperAppBarber.promotion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SuperAppBarber.promotion.model.PromotionServiceEntity;
import com.example.SuperAppBarber.promotion.model.PromotionServiceId;

public interface PromotionServiceRepository
        extends JpaRepository<PromotionServiceEntity, PromotionServiceId> {

    List<PromotionServiceEntity> findByPromotionId(Long promotionId);

    List<PromotionServiceEntity> findByServiceId(Long serviceId);
}