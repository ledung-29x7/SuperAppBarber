package com.example.SuperAppBarber.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SuperAppBarber.common.exception.response.ApiResponse;
import com.example.SuperAppBarber.common.security.SecurityUtil;
import com.example.SuperAppBarber.user.dto.sdi.ChangePasswordRequest;
import com.example.SuperAppBarber.user.dto.sdi.UpdateProfileRequest;
import com.example.SuperAppBarber.user.dto.sdo.UserResponse;
import com.example.SuperAppBarber.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ApiResponse<UserResponse> me() {
        return ApiResponse.<UserResponse>builder()
                .success(true)
                .code("00")
                .message("Success")
                .data(userService.getMe(SecurityUtil.getCurrentUserId()))
                .build();
    }

    @PutMapping("/me")
    public ApiResponse<UserResponse> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request) {
        return ApiResponse.<UserResponse>builder()
                .success(true)
                .code("00")
                .message("Updated")
                .data(userService.updateProfile(SecurityUtil.getCurrentUserId(), request))
                .build();
    }

    @PutMapping("/me/password")
    public ApiResponse<Void> changePassword(
            @Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(SecurityUtil.getCurrentUserId(), request);
        return ApiResponse.<Void>builder()
                .success(true)
                .code("00")
                .message("Password updated")
                .build();
    }
}