package com.example.SuperAppBarber.user.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.SuperAppBarber.user.dto.sdi.AdminUpdateUserRequest;
import com.example.SuperAppBarber.user.dto.sdo.UserResponse;

public interface AdminUserService {

    Page<UserResponse> getUsers(Pageable pageable);

    void updateUser(UUID userId, AdminUpdateUserRequest request);
}
