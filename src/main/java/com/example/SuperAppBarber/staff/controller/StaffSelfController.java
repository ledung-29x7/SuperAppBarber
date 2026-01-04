package com.example.SuperAppBarber.staff.controller;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SuperAppBarber.common.exception.BusinessException;
import com.example.SuperAppBarber.common.exception.enums.ErrorCode;
import com.example.SuperAppBarber.common.exception.response.ApiResponse;
import com.example.SuperAppBarber.common.security.SecurityUtil;
import com.example.SuperAppBarber.staff.dto.sdo.StaffResponse;
import com.example.SuperAppBarber.staff.mapper.StaffMapper;
import com.example.SuperAppBarber.staff.model.StaffEntity;
import com.example.SuperAppBarber.staff.repository.StaffRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/staff/me")
@PreAuthorize("hasRole('STAFF')")
@RequiredArgsConstructor
public class StaffSelfController {

    private final StaffRepository staffRepository;

    @GetMapping
    public ApiResponse<StaffResponse> me() {

        UUID userId = SecurityUtil.getCurrentUserId();

        StaffEntity staff = staffRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        return ApiResponse.<StaffResponse>builder()
                .success(true)
                .code("00")
                .message("Success")
                .data(StaffMapper.toResponse(staff))
                .build();
    }
}
