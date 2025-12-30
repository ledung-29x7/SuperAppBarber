package com.example.SuperAppBarber.user.service;

import java.util.UUID;

import com.example.SuperAppBarber.user.dto.sdi.ChangePasswordRequest;
import com.example.SuperAppBarber.user.dto.sdi.UpdateProfileRequest;
import com.example.SuperAppBarber.user.dto.sdo.UserResponse;

public interface UserService {
    UserResponse getMe(UUID userId);

    UserResponse updateProfile(UUID userId, UpdateProfileRequest request);

    void changePassword(UUID userId, ChangePasswordRequest request);
}
