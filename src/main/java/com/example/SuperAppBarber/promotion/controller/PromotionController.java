package com.example.SuperAppBarber.promotion.controller;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SuperAppBarber.common.exception.response.ApiResponse;
import com.example.SuperAppBarber.promotion.dto.sdo.PricePreviewResponse;
import com.example.SuperAppBarber.promotion.service.PriceCalculator;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/salons/{salonId}/price-preview")
@RequiredArgsConstructor
public class PromotionController {

    private final PriceCalculator priceCalculator;

    @GetMapping
    public ApiResponse<PricePreviewResponse> preview(
            @PathVariable UUID salonId,
            @RequestParam Long serviceId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bookingDate) {
        return ApiResponse.<PricePreviewResponse>builder()
                .success(true)
                .code("00")
                .message("Success")
                .data(priceCalculator.calculate(salonId, serviceId, bookingDate))
                .build();
    }
}
