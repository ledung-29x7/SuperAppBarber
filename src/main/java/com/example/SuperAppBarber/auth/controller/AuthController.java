package com.example.SuperAppBarber.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SuperAppBarber.auth.sdi.LoginRequest;
import com.example.SuperAppBarber.auth.sdi.RegisterRequest;
import com.example.SuperAppBarber.auth.sdi.TokenRefreshRequest;
import com.example.SuperAppBarber.auth.sdo.AuthResponse;
import com.example.SuperAppBarber.auth.sdo.TokenRefreshResponse;
import com.example.SuperAppBarber.auth.service.Interface.AuthService;
import com.example.SuperAppBarber.common.exception.enums.ErrorCode;
import com.example.SuperAppBarber.common.exception.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ApiResponse.<AuthResponse>builder()
                .success(true)
                .code("00")
                .message("Success")
                .data(authService.register(request))
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.<AuthResponse>builder()
                .success(true)
                .code("00")
                .message("Success")
                .data(authService.login(request))
                .build();
    }

    @PostMapping("/refresh")
    public ApiResponse<TokenRefreshResponse> refresh(
            @Valid @RequestBody TokenRefreshRequest request) {
        return ApiResponse.<TokenRefreshResponse>builder()
                .success(true)
                .code("00")
                .message("Success")
                .data(authService.refresh(request))
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        return ApiResponse.<Void>builder()
                .success(true)
                .code(ErrorCode.SUCCESS.getCode())
                .message("Logout success")
                .build();
    }

}
