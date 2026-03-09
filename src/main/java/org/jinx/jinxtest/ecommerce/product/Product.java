package org.jinx.jinxtest.ecommerce.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jinx.jinxtest.ecommerce.converter.CommaSeparatedListConverter;
import org.jinx.jinxtest.ecommerce.converter.JsonMapConverter;
import org.jinx.jinxtest.ecommerce.converter.MoneyConverter;
import org.jinx.jinxtest.ecommerce.party.Vendor;
import org.jinx.jinxtest.ecommerce.vo.Money;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 상품 최상위 엔티티. SINGLE_TABLE 전략으로 단일 테이블에 모든 서브타입 저장.
 * product_type 컬럼으로 서브타입 구분.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)

@Getter
@Setter
@NoArgsConstructor
public abstract class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, unique = true, length = 50)
    private String sku;

    /** 판매가 (Money Converter) */
    @Convert(converter = MoneyConverter.class)
    @Column(name = "price", length = 30, nullable = false)
    private Money price;

    /** 원가 (Money Converter) */
    @Convert(converter = MoneyConverter.class)
    @Column(name = "cost_price", length = 30)
    private Money costPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_status", nullable = false, length = 20)
    private ProductStatus status = ProductStatus.DRAFT;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    /** 이미지 URL 목록 (콤마 Converter) */
    @Convert(converter = CommaSeparatedListConverter.class)
    @Column(name = "image_urls", length = 2000)
    private List<String> imageUrls = new ArrayList<>();

    /** 검색 태그 (콤마 Converter) */
    @Convert(converter = CommaSeparatedListConverter.class)
    @Column(name = "search_tags", length = 1000)
    private List<String> searchTags = new ArrayList<>();

    /** 사양/속성 (JSON Converter) — 자유형식 key-value */
    @Convert(converter = JsonMapConverter.class)
    @Column(name = "specifications", columnDefinition = "TEXT")
    private Map<String, Object> specifications = new HashMap<>();

    @Column(nullable = false)
    private double averageRating = 0.0;

    @Column(nullable = false)
    private int reviewCount = 0;

    /** 등록한 벤더 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    /** 카테고리 (자기 참조 트리) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
