package org.jinx.jinxtest.ecommerce.coupon;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jinx.jinxtest.ecommerce.converter.CommaSeparatedListConverter;
import org.jinx.jinxtest.ecommerce.converter.MoneyConverter;
import org.jinx.jinxtest.ecommerce.vo.Money;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 쿠폰 엔티티. 정액/정률 할인, 최소 주문 금액, 사용 제한 등 복잡한 비즈니스 룰.
 * 특정 카테고리/상품 제한 (콤마 Converter).
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DiscountType discountType;

    /** 할인 금액 (정액 할인용, Money Converter) */
    @Convert(converter = MoneyConverter.class)
    @Column(name = "discount_amount", length = 30)
    private Money discountAmount;

    /** 할인율 (정률 할인용, 0.00 ~ 1.00) */
    @Column(name = "discount_rate")
    private Double discountRate;

    /** 최대 할인 금액 (정률 할인 상한, Money Converter) */
    @Convert(converter = MoneyConverter.class)
    @Column(name = "max_discount_amount", length = 30)
    private Money maxDiscountAmount;

    /** 최소 주문 금액 (Money Converter) */
    @Convert(converter = MoneyConverter.class)
    @Column(name = "minimum_order_amount", length = 30)
    private Money minimumOrderAmount;

    @Column(nullable = false)
    private LocalDateTime validFrom;

    @Column(nullable = false)
    private LocalDateTime validUntil;

    @Column(nullable = false)
    private int totalUsageLimit;    // 전체 사용 가능 횟수 (0 = 무제한)

    @Column(nullable = false)
    private int usedCount = 0;

    @Column(nullable = false)
    private int perCustomerLimit = 1;   // 고객당 사용 횟수 제한

    /** 적용 가능 카테고리 ID 목록 (콤마 Converter) */
    @Convert(converter = CommaSeparatedListConverter.class)
    @Column(name = "applicable_category_ids", length = 500)
    private List<String> applicableCategoryIds = new ArrayList<>();

    /** 제외 상품 ID 목록 (콤마 Converter) */
    @Convert(converter = CommaSeparatedListConverter.class)
    @Column(name = "excluded_product_ids", length = 500)
    private List<String> excludedProductIds = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CouponStatus status = CouponStatus.ACTIVE;

    @Column(nullable = false)
    private boolean isFirstOrderOnly = false;

    @Column(nullable = false)
    private boolean isPremiumCustomerOnly = false;

    public boolean isValid() {
        LocalDateTime now = LocalDateTime.now();
        return status == CouponStatus.ACTIVE
                && now.isAfter(validFrom)
                && now.isBefore(validUntil)
                && (totalUsageLimit == 0 || usedCount < totalUsageLimit);
    }
}
