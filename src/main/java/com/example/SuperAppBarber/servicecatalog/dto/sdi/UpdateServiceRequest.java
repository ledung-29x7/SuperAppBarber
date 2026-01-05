package com.example.SuperAppBarber.servicecatalog.dto.sdi;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateServiceRequest {

    private String name;
    private BigDecimal price;
    private Integer durationMinutes;
    private Boolean active;
}
