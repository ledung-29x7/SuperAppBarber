package com.example.SuperAppBarber.promotion.dto.sdo;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PricePreviewResponse {

    private Long serviceId;
    private BigDecimal originalPrice;
    private BigDecimal finalPrice;
    private Long promotionId;
}
