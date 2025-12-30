package com.example.SuperAppBarber.user.service.Impl;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SuperAppBarber.common.exception.BusinessException;
import com.example.SuperAppBarber.common.exception.enums.ErrorCode;
import com.example.SuperAppBarber.user.dto.sdi.ChangePasswordRequest;
import com.example.SuperAppBarber.user.dto.sdi.UpdateProfileRequest;
import com.example.SuperAppBarber.user.dto.sdo.UserResponse;
import com.example.SuperAppBarber.user.mapper.UserMapper;
import com.example.SuperAppBarber.user.model.UserEntity;
import com.example.SuperAppBarber.user.repository.UserRepository;
import com.example.SuperAppBarber.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse getMe(UUID userId) {
        return UserMapper.toResponse(getUser(userId));
    }

    @Override
    public UserResponse updateProfile(UUID userId, UpdateProfileRequest request) {
        UserEntity user = getUser(userId);

        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());

        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void changePassword(UUID userId, ChangePasswordRequest request) {
        UserEntity user = getUser(userId);

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.DATA_INVALID);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    private UserEntity getUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
    }
}
