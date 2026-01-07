package com.example.SuperAppBarber.servicecatalog.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SuperAppBarber.common.exception.enums.ErrorCode;
import com.example.SuperAppBarber.common.exception.response.ApiResponse;
import com.example.SuperAppBarber.servicecatalog.dto.sdo.ServiceResponse;
import com.example.SuperAppBarber.servicecatalog.service.StaffServiceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/staff/me")
@PreAuthorize("hasRole('STAFF')")
@RequiredArgsConstructor
public class ServiceStaffController {
    private final StaffServiceService staffServiceervice;

    @GetMapping("/services")
    public ApiResponse<List<ServiceResponse>> getMyServices() {

        return ApiResponse.<List<ServiceResponse>>builder()
                .success(true)
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .data(staffServiceervice.getMyAssignedServices())
                .build();
    }
}
