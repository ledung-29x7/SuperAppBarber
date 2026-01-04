package com.example.SuperAppBarber.staff.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SuperAppBarber.common.enums.DayOffStatus;
import com.example.SuperAppBarber.common.exception.response.ApiResponse;
import com.example.SuperAppBarber.common.security.SecurityUtil;
import com.example.SuperAppBarber.staff.dto.sdi.CreateStaffRequest;
import com.example.SuperAppBarber.staff.dto.sdi.UpdateDayOffStatusRequest;
import com.example.SuperAppBarber.staff.dto.sdi.UpdateStaffRequest;
import com.example.SuperAppBarber.staff.dto.sdo.DayOffResponse;
import com.example.SuperAppBarber.staff.dto.sdo.StaffResponse;
import com.example.SuperAppBarber.staff.service.StaffService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/salons/{salonId}")
@RequiredArgsConstructor
@PreAuthorize("hasRole('OWNER')")
public class StaffController {

        private final StaffService staffService;

        @PostMapping("/staff")
        public ApiResponse<StaffResponse> create(
                        @PathVariable UUID salonId,
                        @RequestBody @Valid CreateStaffRequest request) {
                return ApiResponse.<StaffResponse>builder()
                                .success(true)
                                .code("00")
                                .message("Created")
                                .data(staffService.create(salonId, request))
                                .build();
        }

        @GetMapping("/staffs")
        public ApiResponse<List<StaffResponse>> list(@PathVariable UUID salonId) {
                return ApiResponse.<List<StaffResponse>>builder()
                                .success(true)
                                .code("00")
                                .message("Success")
                                .data(staffService.list(salonId))
                                .build();
        }

        @PutMapping("/staff/{staffId}")
        public ApiResponse<StaffResponse> update(
                        @PathVariable UUID salonId,
                        @PathVariable UUID staffId,
                        @RequestBody UpdateStaffRequest request) {
                return ApiResponse.<StaffResponse>builder()
                                .success(true)
                                .code("00")
                                .message("Updated")
                                .data(staffService.update(staffId, salonId, request))
                                .build();
        }

        @PutMapping("/approve/{dayOffId}")
        public ApiResponse<Void> approve(
                        @PathVariable UUID salonId,
                        @PathVariable Long dayOffId,
                        @RequestBody UpdateDayOffStatusRequest request) {
                staffService.updateStatus(
                                salonId,
                                dayOffId,
                                request.getStatus(),
                                SecurityUtil.getCurrentUserId());

                return ApiResponse.<Void>builder()
                                .success(true)
                                .code("00")
                                .message("Updated")
                                .build();
        }

        @GetMapping("/day-off")
        public ApiResponse<List<DayOffResponse>> getDayOff(
                        @PathVariable UUID salonId,
                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                        @RequestParam(required = false) DayOffStatus status,
                        @RequestParam(required = false) UUID staffId) {
                return ApiResponse.<List<DayOffResponse>>builder()
                                .success(true)
                                .code("00")
                                .message("Success")
                                .data(staffService.getDayOffForApproval(
                                                salonId, fromDate, toDate, status, staffId))
                                .build();
        }

}
