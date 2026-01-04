package com.example.SuperAppBarber.staff.dto.sdo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.SuperAppBarber.common.enums.DayOffStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DayOffResponse {

    private Long id;
    private UUID staffId;
    private UUID salonId;

    private LocalDate startDate;
    private LocalDate endDate;

    private String reason;
    private DayOffStatus status;

    private UUID approvedBy;
    private LocalDateTime approvedAt;

    private LocalDateTime createdAt;
}
