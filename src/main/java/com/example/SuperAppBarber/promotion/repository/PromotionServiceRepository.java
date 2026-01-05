package com.example.SuperAppBarber.promotion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.SuperAppBarber.promotion.model.PromotionServiceEntity;
import com.example.SuperAppBarber.promotion.model.PromotionServiceId;

public interface PromotionServiceRepository
        extends JpaRepository<PromotionServiceEntity, PromotionServiceId> {

    List<PromotionServiceEntity> findByIdPromotionId(Long promotionId);

    List<PromotionServiceEntity> findByIdServiceId(Long serviceId);

    @Query("""
                SELECT COUNT(ps) > 0
                FROM PromotionServiceEntity ps
                WHERE ps.id.promotionId = :promotionId
                  AND ps.id.serviceId = :serviceId
            """)
    boolean existsByPromotionAndService(
            @Param("promotionId") Long promotionId,
            @Param("serviceId") Long serviceId);

    @Query("""
                SELECT COUNT(ps)
                FROM PromotionServiceEntity ps
                WHERE ps.id.promotionId = :promotionId
            """)
    long countServicesOfPromotion(@Param("promotionId") Long promotionId);

}