package com.example.SuperAppBarber.common.base;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class SoftDeleteEntity extends BaseEntity {

    @Column(name = "deleted_at")
    protected LocalDateTime deletedAt;
}
