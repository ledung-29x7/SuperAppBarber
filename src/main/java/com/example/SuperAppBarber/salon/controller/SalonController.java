package com.example.SuperAppBarber.salon.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SuperAppBarber.common.exception.response.ApiResponse;
import com.example.SuperAppBarber.common.security.SecurityUtil;
import com.example.SuperAppBarber.salon.dto.sdi.CreateSalonRequest;
import com.example.SuperAppBarber.salon.dto.sdi.UpdateSalonRequest;
import com.example.SuperAppBarber.salon.dto.sdo.SalonResponse;
import com.example.SuperAppBarber.salon.service.SalonService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/salons")
@RequiredArgsConstructor
@PreAuthorize("hasRole('OWNER')")
public class SalonController {

    private final SalonService salonService;

    @PostMapping
    public ApiResponse<SalonResponse> create(@RequestBody @Valid CreateSalonRequest request) {

        return ApiResponse.<SalonResponse>builder()
                .success(true)
                .code("00")
                .message("Created")
                .data(salonService.create(request, SecurityUtil.getCurrentUserId()))
                .build();
    }

    @PutMapping("/{salonId}")
    public ApiResponse<SalonResponse> update(
            @PathVariable UUID salonId,
            @RequestBody UpdateSalonRequest request) {
        return ApiResponse.<SalonResponse>builder()
                .success(true)
                .code("00")
                .message("Updated")
                .data(salonService.update(salonId, request, SecurityUtil.getCurrentUserId()))
                .build();
    }

    @GetMapping("/me")
    public ApiResponse<List<SalonResponse>> mySalons() {
        return ApiResponse.<List<SalonResponse>>builder()
                .success(true)
                .code("00")
                .message("Success")
                .data(salonService.getMySalons(SecurityUtil.getCurrentUserId()))
                .build();
    }

    @GetMapping("/{salonId}")
    public ApiResponse<SalonResponse> mySalon(@PathVariable UUID salonId) {
        UUID userId = SecurityUtil.getCurrentUserId();
        return ApiResponse.<SalonResponse>builder()
                .success(true)
                .code("00")
                .message("Success")
                .data(salonService.getSalon(userId, salonId))
                .build();
    }
}
