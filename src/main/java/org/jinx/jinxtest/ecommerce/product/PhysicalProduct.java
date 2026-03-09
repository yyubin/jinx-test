package org.jinx.jinxtest.ecommerce.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 실물 상품 — SINGLE_TABLE 2단계 (Product → PhysicalProduct).
 * 일반 물리 상품 (Electronics, Clothing과 별개 타입).
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class PhysicalProduct extends Product {

    private double weightKg;
    private double lengthCm;
    private double widthCm;
    private double heightCm;

    @Column(length = 100)
    private String countryOfOrigin;

    @Column(nullable = false)
    private boolean requiresShipping = true;

    @Column(nullable = false)
    private boolean isFragile = false;

    @Column(nullable = false)
    private boolean isHazardous = false;
}
