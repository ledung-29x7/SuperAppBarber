package com.example.SuperAppBarber.promotion.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "promotion_services")
@Getter
@Setter
public class PromotionServiceEntity {

    @EmbeddedId
    private PromotionServiceId id;
}
