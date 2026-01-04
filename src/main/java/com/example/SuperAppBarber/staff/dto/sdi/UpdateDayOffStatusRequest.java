package com.example.SuperAppBarber.staff.dto.sdi;

import com.example.SuperAppBarber.common.enums.DayOffStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDayOffStatusRequest {
    @NotNull
    private DayOffStatus status;
}
