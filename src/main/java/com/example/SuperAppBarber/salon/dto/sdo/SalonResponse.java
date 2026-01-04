package com.example.SuperAppBarber.salon.dto.sdo;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalonResponse {

    private UUID salonId;
    private String salonName;
    private String address;
    private String phone;
    private UUID ownerId;
}
