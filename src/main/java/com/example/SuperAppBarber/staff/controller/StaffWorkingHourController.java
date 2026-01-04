package com.example.SuperAppBarber.staff.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SuperAppBarber.common.exception.response.ApiResponse;
import com.example.SuperAppBarber.staff.dto.sdi.StaffWorkingHourRequest;
import com.example.SuperAppBarber.staff.service.StaffService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/salons/{salonId}/staff/{staffId}/working-hours")
@RequiredArgsConstructor
@PreAuthorize("hasRole('OWNER')")
public class StaffWorkingHourController {

    private final StaffService staffService;

    @PutMapping
    public ApiResponse<Void> setWorkingHours(
            @PathVariable UUID salonId,
            @PathVariable UUID staffId,
            @RequestBody @Valid List<StaffWorkingHourRequest> requests) {
        staffService.setWorkingHours(salonId, staffId, requests);

        return ApiResponse.<Void>builder()
                .success(true)
                .code("00")
                .message("Working hours updated")
                .build();
    }
}
