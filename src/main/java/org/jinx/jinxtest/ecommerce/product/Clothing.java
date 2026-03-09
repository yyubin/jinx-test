package org.jinx.jinxtest.ecommerce.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jinx.jinxtest.ecommerce.converter.ColorConverter;
import org.jinx.jinxtest.ecommerce.converter.CommaSeparatedListConverter;
import org.jinx.jinxtest.ecommerce.vo.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * 의류 — SINGLE_TABLE 2단계 (Product → Clothing).
 * 물리적 속성 + 색상 (Color Converter), 사이즈, 소재 등 의류 특화 속성 인라인.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Clothing extends Product {

    // ── PhysicalProduct 공통 속성 ──
    private double weightKg;
    private double lengthCm;
    private double widthCm;
    private double heightCm;

    @Column(length = 100)
    private String countryOfOrigin;

    @Column(nullable = false)
    private boolean requiresShipping = true;

    // ── Clothing 특화 속성 ──
    @Column(length = 100)
    private String brand;

    /** 주 색상 (Color VO → Hex Converter) */
    @Convert(converter = ColorConverter.class)
    @Column(name = "primary_color", length = 10)
    private Color primaryColor;

    /** 보조 색상 (Color VO → Hex Converter) */
    @Convert(converter = ColorConverter.class)
    @Column(name = "secondary_color", length = 10)
    private Color secondaryColor;

    /** 사용 가능 사이즈 목록 (콤마 Converter) */
    @Convert(converter = CommaSeparatedListConverter.class)
    @Column(name = "available_sizes", length = 200)
    private List<String> availableSizes = new ArrayList<>();

    /** 소재 구성 (콤마 Converter) */
    @Convert(converter = CommaSeparatedListConverter.class)
    @Column(name = "materials", length = 300)
    private List<String> materials = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ClothingGender targetGender;

    @Column(length = 50)
    private String careInstructions;

    @Column(nullable = false)
    private boolean isWashable = true;
}
