package com.example.SuperAppBarber.staff.dto.sdi;

import java.time.LocalTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffWorkingHourRequest {
    @Min(1)
    @Max(7)
    private int dayOfWeek; // 1-7
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
}
