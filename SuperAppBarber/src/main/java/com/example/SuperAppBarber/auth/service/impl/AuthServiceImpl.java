package com.example.SuperAppBarber.auth.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SuperAppBarber.auth.jwt.JwtService;
import com.example.SuperAppBarber.auth.sdi.LoginRequest;
import com.example.SuperAppBarber.auth.sdi.RegisterRequest;
import com.example.SuperAppBarber.auth.sdi.TokenRefreshRequest;
import com.example.SuperAppBarber.auth.sdo.AuthResponse;
import com.example.SuperAppBarber.auth.sdo.TokenRefreshResponse;
import com.example.SuperAppBarber.auth.service.Interface.AuthService;
import com.example.SuperAppBarber.common.enums.Role;
import com.example.SuperAppBarber.common.enums.UserStatus;
import com.example.SuperAppBarber.common.exception.BusinessException;
import com.example.SuperAppBarber.common.exception.enums.ErrorCode;
import com.example.SuperAppBarber.user.model.UserEntity;
import com.example.SuperAppBarber.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new BusinessException(ErrorCode.DATA_INVALID);
        }

        UserEntity user = UserEntity.builder()
                // .userId(UUID.randomUUID())
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(UserStatus.ACTIVE)
                .role(Role.CUSTOMER)
                // .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return new AuthResponse(
                jwtService.generateAccessToken(user.getUserId(), user.getRole().name()),
                jwtService.generateRefreshToken(user.getUserId()));
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        UserEntity user = userRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new BusinessException(ErrorCode.UNAUTHORIZED));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED);
        }

        if (user.getStatus() == UserStatus.BLOCKED) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        return new AuthResponse(
                jwtService.generateAccessToken(user.getUserId(), user.getRole().name()),
                jwtService.generateRefreshToken(user.getUserId()));
    }

    @Override
    public TokenRefreshResponse refresh(TokenRefreshRequest request) {

        String token = request.getRefreshToken();

        if (!jwtService.validateToken(token)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED);
        }

        if (!"REFRESH".equals(jwtService.extractTokenType(token))) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED);
        }

        UUID userId = jwtService.extractUserId(token);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.UNAUTHORIZED));

        if (user.getStatus() == UserStatus.BLOCKED) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        String newAccessToken = jwtService.generateAccessToken(user.getUserId(), user.getRole().name());
        String newRefreshToke = jwtService.generateRefreshToken(userId);

        return new TokenRefreshResponse(newAccessToken, newRefreshToke);
    }

}
