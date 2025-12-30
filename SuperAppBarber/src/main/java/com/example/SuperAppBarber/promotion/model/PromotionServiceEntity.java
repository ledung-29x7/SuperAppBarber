package com.example.SuperAppBarber.promotion.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "promotion_services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(PromotionServiceId.class)
public class PromotionServiceEntity {

    @Id
    @Column(name = "promotion_id")
    private Long promotionId;

    @Id
    @Column(name = "service_id")
    private Long serviceId;
}
