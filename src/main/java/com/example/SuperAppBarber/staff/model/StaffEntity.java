package com.example.SuperAppBarber.staff.model;

import java.util.UUID;

import com.example.SuperAppBarber.common.base.BaseEntity;
import com.example.SuperAppBarber.common.enums.StaffRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "staff")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffEntity extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "staff_id")
    private UUID staffId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "salon_id", nullable = false)
    private UUID salonId;

    private String name;

    @Enumerated(EnumType.STRING)
    private StaffRole role;

    private Boolean active;
}
