package com.example.SuperAppBarber.staff.mapper;

import com.example.SuperAppBarber.staff.dto.sdo.StaffResponse;
import com.example.SuperAppBarber.staff.model.StaffEntity;

public class StaffMapper {

    public static StaffResponse toResponse(StaffEntity e) {
        return StaffResponse.builder()
                .staffId(e.getStaffId())
                .userId(e.getUserId())
                .salonId(e.getSalonId())
                .name(e.getName())
                .role(e.getRole().name())
                .active(e.getActive())
                .build();
    }
}
