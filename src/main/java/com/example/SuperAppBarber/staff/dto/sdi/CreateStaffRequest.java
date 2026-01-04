package com.example.SuperAppBarber.staff.dto.sdi;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateStaffRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    private String email;
}
