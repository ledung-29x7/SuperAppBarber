package com.example.SuperAppBarber.servicecatalog.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SuperAppBarber.common.exception.response.ApiResponse;
import com.example.SuperAppBarber.servicecatalog.dto.sdi.CreateServiceRequest;
import com.example.SuperAppBarber.servicecatalog.dto.sdi.UpdateServiceRequest;
import com.example.SuperAppBarber.servicecatalog.dto.sdo.ServiceResponse;
import com.example.SuperAppBarber.servicecatalog.service.ServiceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/salons/{salonId}/services")
@RequiredArgsConstructor
@PreAuthorize("hasRole('OWNER')")
public class ServiceController {

    private final ServiceService serviceService;

    @PostMapping
    public ApiResponse<ServiceResponse> create(
            @PathVariable UUID salonId,
            @RequestBody @Valid CreateServiceRequest request) {
        return ApiResponse.<ServiceResponse>builder()
                .success(true)
                .code("00")
                .message("Created")
                .data(serviceService.create(salonId, request))
                .build();
    }

    @PutMapping("/{serviceId}")
    public ApiResponse<ServiceResponse> update(
            @PathVariable UUID salonId,
            @PathVariable Long serviceId,
            @RequestBody UpdateServiceRequest request) {
        return ApiResponse.<ServiceResponse>builder()
                .success(true)
                .code("00")
                .message("Updated")
                .data(serviceService.update(salonId, serviceId, request))
                .build();
    }

    @DeleteMapping("/{serviceId}")
    public ApiResponse<Object> delete(
            @PathVariable UUID salonId,
            @PathVariable Long serviceId) {
        serviceService.delete(salonId, serviceId);
        return ApiResponse.builder()
                .success(true)
                .code("00")
                .message("Deleted")
                .data(null)
                .build();
    }

    @GetMapping
    public ApiResponse<List<ServiceResponse>> getAll(
            @PathVariable UUID salonId) {
        return ApiResponse.<List<ServiceResponse>>builder()
                .success(true)
                .code("00")
                .message("Success")
                .data(serviceService.getAll(salonId))
                .build();
    }
}
