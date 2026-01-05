package com.example.SuperAppBarber.servicecatalog.dto.sdo;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceResponse {

    private Long serviceId;
    private String name;
    private BigDecimal price;
    private Integer durationMinutes;
    private Boolean active;
}
