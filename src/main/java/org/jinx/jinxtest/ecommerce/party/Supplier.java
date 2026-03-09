package org.jinx.jinxtest.ecommerce.party;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jinx.jinxtest.ecommerce.converter.MoneyConverter;
import org.jinx.jinxtest.ecommerce.vo.Money;

/**
 * 공급자(서플라이어) — Party → Organization → Supplier (3단계 JOINED).
 * 재고를 공급하는 도매상/제조사.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Supplier extends Organization {

    @Column(nullable = false, length = 100)
    private String supplyCategory;

    /** 최소 주문 금액 (Money Converter) */
    @Convert(converter = MoneyConverter.class)
    @Column(name = "minimum_order_amount", length = 30)
    private Money minimumOrderAmount;

    /** 평균 리드타임 (일 단위) */
    @Column(nullable = false)
    private int averageLeadTimeDays;

    @Column(nullable = false)
    private boolean isPreferredSupplier = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SupplierTier supplierTier = SupplierTier.STANDARD;

    @Column(length = 200)
    private String certificationInfo;
}
