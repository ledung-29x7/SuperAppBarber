package com.example.SuperAppBarber.servicecatalog.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "staff_services")
@Getter
@Setter
public class StaffServiceEntity {

    @EmbeddedId
    private StaffServiceId id;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime deletedAt;
}
