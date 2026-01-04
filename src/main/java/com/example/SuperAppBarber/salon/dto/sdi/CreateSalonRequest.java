package com.example.SuperAppBarber.salon.dto.sdi;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSalonRequest {

    @NotBlank
    private String salonName;

    private String address;

    private String phone;
}
