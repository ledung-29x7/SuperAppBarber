package com.example.SuperAppBarber.servicecatalog.dto.sdo;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffServiceResponse {

    private UUID staffId;
    private Long serviceId;

    private String serviceName;
    private BigDecimal price;
    private Integer durationMinutes;
}
