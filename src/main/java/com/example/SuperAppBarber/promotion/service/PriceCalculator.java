package com.example.SuperAppBarber.promotion.service;

import java.time.LocalDate;
import java.util.UUID;

import com.example.SuperAppBarber.promotion.dto.sdo.PricePreviewResponse;

public interface PriceCalculator {

    PricePreviewResponse calculate(
            UUID salonId,
            Long serviceId,
            LocalDate bookingDate);
}
