package com.example.SuperAppBarber.staff.dto.sdo;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffResponse {

    private UUID staffId;
    private UUID userId;
    private UUID salonId;
    private String name;
    private String role;
    private Boolean active;
}
