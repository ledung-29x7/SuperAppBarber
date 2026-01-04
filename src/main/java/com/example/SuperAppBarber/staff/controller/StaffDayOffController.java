package com.example.SuperAppBarber.staff.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SuperAppBarber.common.exception.response.ApiResponse;
import com.example.SuperAppBarber.common.security.SecurityUtil;
import com.example.SuperAppBarber.staff.dto.sdi.CreateDayOffRequest;
import com.example.SuperAppBarber.staff.service.StaffService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/staff/me/day-off")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STAFF')")
public class StaffDayOffController {

    private final StaffService dayOffService;

    @PostMapping
    public ApiResponse<Void> requestDayOff(
            @RequestBody @Valid CreateDayOffRequest request) {
        dayOffService.requestDayOff(SecurityUtil.getCurrentUserId(), request);

        return ApiResponse.<Void>builder()
                .success(true)
                .code("00")
                .message("Day off requested")
                .build();
    }
}
