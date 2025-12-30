package com.example.SuperAppBarber.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SuperAppBarber.user.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByPhone(String phone);

    boolean existsByPhone(String phone);

    Page<UserEntity> findAll(Pageable pageable);
}
