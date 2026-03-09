package org.jinx.jinxtest.ecommerce.party;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jinx.jinxtest.ecommerce.converter.CommaSeparatedListConverter;
import org.jinx.jinxtest.ecommerce.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 판매자(벤더) — Party → Organization → Vendor (3단계 JOINED).
 * 상품을 직접 등록하고 판매하는 주체.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Vendor extends Organization {

    @Column(nullable = false, length = 100)
    private String storeName;

    @Column(columnDefinition = "TEXT")
    private String storeDescription;

    @Column(nullable = false, precision = 3, scale = 2)
    private BigDecimal commissionRate;   // 플랫폼 수수료율 (예: 0.05 = 5%)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private VendorStatus vendorStatus = VendorStatus.PENDING_APPROVAL;

    /** 취급 카테고리 목록 (콤마 Converter) */
    @Convert(converter = CommaSeparatedListConverter.class)
    @Column(name = "categories", length = 500)
    private List<String> categories = new ArrayList<>();

    @Column(nullable = false)
    private double averageRating = 0.0;

    @Column(nullable = false)
    private int totalReviews = 0;

    /** 이 벤더가 등록한 상품 목록 */
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();
}
