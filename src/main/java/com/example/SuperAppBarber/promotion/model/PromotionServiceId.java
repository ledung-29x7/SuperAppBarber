package com.example.SuperAppBarber.promotion.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class PromotionServiceId implements Serializable {
    @Column(name = "promotion_id")
    private Long promotionId;

    @Column(name = "service_id")
    private Long serviceId;
}
