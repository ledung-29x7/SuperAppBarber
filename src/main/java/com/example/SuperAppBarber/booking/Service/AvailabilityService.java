package com.example.SuperAppBarber.booking.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.example.SuperAppBarber.booking.dto.sdo.AvailabilityResponse;

public interface AvailabilityService {

    List<AvailabilityResponse> getAvailability(
            UUID salonId,
            Long serviceId,
            LocalDate date);
}
