package com.example.SuperAppBarber.user.mapper;

import com.example.SuperAppBarber.user.dto.sdo.UserResponse;
import com.example.SuperAppBarber.user.model.UserEntity;

public class UserMapper {

    public static UserResponse toResponse(UserEntity user) {
        if (user == null)
            return null;

        return UserResponse.builder()
                .userId(user.getUserId())
                .phone(user.getPhone())
                .email(user.getEmail())
                .role(user.getRole().name())
                .status(user.getStatus().name())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
