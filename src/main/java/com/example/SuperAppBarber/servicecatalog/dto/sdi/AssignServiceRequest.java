package com.example.SuperAppBarber.servicecatalog.dto.sdi;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignServiceRequest {

    @NotNull
    private Long serviceId;
}
