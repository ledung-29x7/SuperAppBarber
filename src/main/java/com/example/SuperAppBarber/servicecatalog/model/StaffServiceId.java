package com.example.SuperAppBarber.servicecatalog.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class StaffServiceId implements Serializable {

    @Column(name = "staff_id")
    private UUID staffId;

    @Column(name = "service_id")
    private Long serviceId;
}
