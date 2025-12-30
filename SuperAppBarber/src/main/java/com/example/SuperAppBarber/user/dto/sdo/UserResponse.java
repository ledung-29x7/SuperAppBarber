package com.example.SuperAppBarber.user.dto.sdo;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.SuperAppBarber.user.model.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private UUID userId;

    private String phone;

    private String email;

    private String role;

    private String status;

    private LocalDateTime createdAt;
}
