package com.example.SuperAppBarber.salon.mapper;

import com.example.SuperAppBarber.salon.dto.sdo.SalonResponse;
import com.example.SuperAppBarber.salon.model.SalonEntity;

public class SalonMapper {

    public static SalonResponse toResponse(SalonEntity entity) {
        return SalonResponse.builder()
                .salonId(entity.getSalonId())
                .salonName(entity.getSalonName())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .ownerId(entity.getOwnerId())
                .build();
    }
}
