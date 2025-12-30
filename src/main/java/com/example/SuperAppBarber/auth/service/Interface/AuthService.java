package com.example.SuperAppBarber.auth.service.Interface;

import com.example.SuperAppBarber.auth.sdi.LoginRequest;
import com.example.SuperAppBarber.auth.sdi.RegisterRequest;
import com.example.SuperAppBarber.auth.sdi.TokenRefreshRequest;
import com.example.SuperAppBarber.auth.sdo.AuthResponse;
import com.example.SuperAppBarber.auth.sdo.TokenRefreshResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    TokenRefreshResponse refresh(TokenRefreshRequest request);
}