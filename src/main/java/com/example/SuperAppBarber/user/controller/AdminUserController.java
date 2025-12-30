package com.example.SuperAppBarber.user.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SuperAppBarber.common.exception.response.ApiResponse;
import com.example.SuperAppBarber.user.dto.sdi.AdminUpdateUserRequest;
import com.example.SuperAppBarber.user.dto.sdo.UserResponse;
import com.example.SuperAppBarber.user.service.AdminUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping
    public ApiResponse<Page<UserResponse>> list(Pageable pageable) {
        return ApiResponse.<Page<UserResponse>>builder()
                .success(true)
                .code("00")
                .message("Success")
                .data(adminUserService.getUsers(pageable))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(
            @PathVariable UUID id,
            @RequestBody AdminUpdateUserRequest request) {
        adminUserService.updateUser(id, request);

        return ApiResponse.<Void>builder()
                .success(true)
                .code("00")
                .message("Updated")
                .build();
    }
}
