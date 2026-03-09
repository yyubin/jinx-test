package org.jinx.jinxtest.ecommerce.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jinx.jinxtest.ecommerce.converter.CommaSeparatedListConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 전자제품 — SINGLE_TABLE 2단계 (Product → Electronics).
 * 물리적 속성(무게/크기) + 전자제품 특화 속성 인라인.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Electronics extends Product {

    // ── PhysicalProduct 공통 속성 ──
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

    // ── Electronics 특화 속성 ──
    @Column(length = 100)
    private String brand;

    @Column(length = 100)
    private String modelNumber;

    @Column(nullable = false)
    private int warrantyMonths = 12;

    @Column(nullable = false)
    private int voltage = 220;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private EnergyRating energyRating;

    /** 지원 커넥터 목록 (콤마 Converter) */
    @Convert(converter = CommaSeparatedListConverter.class)
    @Column(name = "connectors", length = 300)
    private List<String> connectors = new ArrayList<>();

    @Column(nullable = false)
    private boolean hasRemoteControl = false;

    @Column(nullable = false)
    private boolean isSmartDevice = false;
}
