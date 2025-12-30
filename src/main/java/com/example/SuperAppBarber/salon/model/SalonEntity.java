package com.example.SuperAppBarber.salon.model;

import java.util.UUID;

import com.example.SuperAppBarber.common.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "salons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalonEntity extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "salon_id")
    private UUID salonId;

    @Column(name = "salon_name")
    private String salonName;

    private String address;
    private String phone;

    @Column(name = "owner_id")
    private UUID ownerId;
}