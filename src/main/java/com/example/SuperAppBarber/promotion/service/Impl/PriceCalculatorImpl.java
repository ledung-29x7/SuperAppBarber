package com.example.SuperAppBarber.promotion.service.Impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.SuperAppBarber.common.exception.BusinessException;
import com.example.SuperAppBarber.common.exception.enums.ErrorCode;
import com.example.SuperAppBarber.promotion.dto.sdo.PricePreviewResponse;
import com.example.SuperAppBarber.promotion.model.PromotionEntity;
import com.example.SuperAppBarber.promotion.repository.PromotionRepository;
import com.example.SuperAppBarber.promotion.repository.PromotionServiceRepository;
import com.example.SuperAppBarber.promotion.service.PriceCalculator;
import com.example.SuperAppBarber.servicecatalog.model.ServiceEntity;
import com.example.SuperAppBarber.servicecatalog.repository.ServiceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PriceCalculatorImpl implements PriceCalculator {

    private final ServiceRepository serviceRepository;
    private final PromotionRepository promotionRepository;
    private final PromotionServiceRepository promotionServiceRepository;

    @Override
    public PricePreviewResponse calculate(
            UUID salonId,
            Long serviceId,
            LocalDate bookingDate) {
        ServiceEntity service = serviceRepository
                .findByServiceIdAndSalonIdAndDeletedAtIsNull(serviceId, salonId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        BigDecimal originalPrice = service.getPrice();
        BigDecimal finalPrice = originalPrice;

        List<PromotionEntity> promotions = promotionRepository.findActivePromotions(salonId, bookingDate);

        PromotionEntity applied = null;

        for (PromotionEntity p : promotions) {
            if (appliesToService(p.getPromotionId(), serviceId)) {
                finalPrice = applyDiscount(originalPrice, p);
                applied = p;
                break;
            }
        }

        PricePreviewResponse r = new PricePreviewResponse();
        r.setServiceId(serviceId);
        r.setOriginalPrice(originalPrice);
        r.setFinalPrice(finalPrice);
        r.setPromotionId(applied != null ? applied.getPromotionId() : null);

        return r;
    }

    private BigDecimal applyDiscount(BigDecimal price, PromotionEntity p) {

        BigDecimal result = switch (p.getDiscountType()) {

            case PERCENT -> {
                BigDecimal percent = p.getDiscountValue()
                        .divide(BigDecimal.valueOf(100));
                yield price.subtract(price.multiply(percent));
            }

            case FIXED -> price.subtract(p.getDiscountValue());
        };

        return result.max(BigDecimal.ZERO);
    }

    private boolean appliesToService(Long promotionId, Long serviceId) {

        long assignedCount = promotionServiceRepository.countServicesOfPromotion(promotionId);

        // Không gán service nào → áp dụng toàn salon
        if (assignedCount == 0) {
            return true;
        }

        // Có gán → check service có thuộc promotion không
        return promotionServiceRepository
                .existsByPromotionAndService(promotionId, serviceId);
    }

}
