package com.example.SuperAppBarber.salon.dto.sdi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSalonRequest {

    private String salonName;
    private String address;
    private String phone;
}
