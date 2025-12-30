package com.example.SuperAppBarber.promotion.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionServiceId implements Serializable {
    private Long promotionId;
    private Long serviceId;
}
