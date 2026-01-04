package com.example.SuperAppBarber.salon.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SuperAppBarber.common.exception.response.ApiResponse;
import com.example.SuperAppBarber.salon.dto.sdo.SalonResponse;
import com.example.SuperAppBarber.salon.mapper.SalonMapper;
import com.example.SuperAppBarber.salon.repository.SalonRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/salons")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminSalonController {

    private final SalonRepository salonRepository;

    @GetMapping
    public ApiResponse<List<SalonResponse>> list() {
        return ApiResponse.<List<SalonResponse>>builder()
                .success(true)
                .code("00")
                .message("Success")
                .data(
                        salonRepository.findAll()
                                .stream()
                                .map(SalonMapper::toResponse)
                                .toList())
                .build();
    }
}
