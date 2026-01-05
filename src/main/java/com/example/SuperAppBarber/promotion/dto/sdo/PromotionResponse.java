package com.example.SuperAppBarber.promotion.dto.sdo;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromotionResponse {

    private Long id;
    private String name;
    private String discountType;
    private BigDecimal discountValue;
    private Boolean active;
}
