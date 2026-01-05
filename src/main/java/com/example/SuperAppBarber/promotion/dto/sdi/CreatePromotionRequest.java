package com.example.SuperAppBarber.promotion.dto.sdi;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePromotionRequest {

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String discountType; // PERCENT | FIXED

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal discountValue;

    private LocalDate startDate;
    private LocalDate endDate;

    private List<Long> serviceIds; // null = apply all services
}
