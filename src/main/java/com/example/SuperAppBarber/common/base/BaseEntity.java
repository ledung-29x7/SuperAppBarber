package com.example.SuperAppBarber.common.base;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {
    @Column(name = "created_at")
    protected LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Column(name = "deleted_at")
    protected LocalDateTime deletedAt;
}
