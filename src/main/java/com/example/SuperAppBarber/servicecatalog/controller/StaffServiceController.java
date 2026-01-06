package com.example.SuperAppBarber.servicecatalog.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SuperAppBarber.common.exception.response.ApiResponse;
import com.example.SuperAppBarber.servicecatalog.dto.sdi.AssignServiceRequest;
import com.example.SuperAppBarber.servicecatalog.dto.sdo.StaffServiceResponse;
import com.example.SuperAppBarber.servicecatalog.service.StaffServiceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/salons/{salonId}/staff/{staffId}/services")
@RequiredArgsConstructor
@PreAuthorize("hasRole('OWNER')")
public class StaffServiceController {

        private final StaffServiceService staffServiceService;

        @PostMapping
        public ApiResponse<List<StaffServiceResponse>> assign(
                        @PathVariable UUID salonId,
                        @PathVariable UUID staffId,
                        @RequestBody @Valid AssignServiceRequest request) {
                return ApiResponse.<List<StaffServiceResponse>>builder()
                                .success(true)
                                .code("00")
                                .message("Assigned")
                                .data(
                                                staffServiceService.assign(
                                                                salonId, staffId, request.getServiceId()))
                                .build();
        }

        @DeleteMapping("/{serviceId}")
        public ApiResponse<List<StaffServiceResponse>> unassign(
                        @PathVariable UUID salonId,
                        @PathVariable UUID staffId,
                        @PathVariable Long serviceId) {
                return ApiResponse.<List<StaffServiceResponse>>builder()
                                .success(true)
                                .code("00")
                                .message("Unassigned")
                                .data(
                                                staffServiceService.unassign(
                                                                salonId, staffId, serviceId))
                                .build();
        }

        @GetMapping
        public ApiResponse<List<StaffServiceResponse>> getServices(
                        @PathVariable UUID salonId,
                        @PathVariable UUID staffId) {
                return ApiResponse.<List<StaffServiceResponse>>builder()
                                .success(true)
                                .code("00")
                                .message("Success")
                                .data(staffServiceService.getServicesByStaff(salonId, staffId))
                                .build();
        }
}
