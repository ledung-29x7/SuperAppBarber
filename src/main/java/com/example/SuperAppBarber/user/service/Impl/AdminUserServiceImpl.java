package com.example.SuperAppBarber.user.service.Impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.SuperAppBarber.common.enums.Role;
import com.example.SuperAppBarber.common.enums.UserStatus;
import com.example.SuperAppBarber.common.exception.BusinessException;
import com.example.SuperAppBarber.common.exception.enums.ErrorCode;
import com.example.SuperAppBarber.user.dto.sdi.AdminUpdateUserRequest;
import com.example.SuperAppBarber.user.dto.sdo.UserResponse;
import com.example.SuperAppBarber.user.mapper.UserMapper;
import com.example.SuperAppBarber.user.model.UserEntity;
import com.example.SuperAppBarber.user.repository.UserRepository;
import com.example.SuperAppBarber.user.service.AdminUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;

    @Override
    public Page<UserResponse> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserMapper::toResponse);
    }

    @Override
    public void updateUser(UUID userId, AdminUpdateUserRequest request) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        if (request.getRole() != null) {
            user.setRole(Role.valueOf(request.getRole()));
        }

        if (request.getStatus() != null) {
            user.setStatus(UserStatus.valueOf(request.getStatus()));
        }

        userRepository.save(user);
    }

}
