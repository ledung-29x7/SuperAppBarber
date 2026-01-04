package com.example.SuperAppBarber.staff.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.SuperAppBarber.common.enums.DayOffStatus;

@Entity
@Table(name = "day_off")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DayOffEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "staff_id")
    private UUID staffId;

    @Column(name = "salon_id", nullable = false)
    private UUID salonId;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    private String reason;

    @Enumerated(EnumType.STRING)
    private DayOffStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "approved_by")
    private UUID approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
