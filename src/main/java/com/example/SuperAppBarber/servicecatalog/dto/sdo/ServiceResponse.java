package com.example.SuperAppBarber.servicecatalog.dto.sdo;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse {

    private Long serviceId;
    private String name;
    private BigDecimal price;
    private Integer durationMinutes;
    private Boolean active;
}
