package org.jinx.jinxtest.ecommerce.party;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 프리미엄 고객 — Party → Person → Customer → PremiumCustomer (4단계 JOINED).
 * 전용 할인율, 전담 매니저, 구독 정보 보유.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class PremiumCustomer extends Customer {

    /** 전용 추가 할인율 (0.00 ~ 1.00) */
    @Column(nullable = false, precision = 5, scale = 4)
    private BigDecimal exclusiveDiscountRate = BigDecimal.ZERO;

    /** 전담 매니저 (자기 참조: Manager 엔티티) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dedicated_manager_id")
    private Manager dedicatedManager;

    @Column(nullable = false)
    private LocalDate membershipStartDate;

    private LocalDate membershipEndDate;

    @Column(nullable = false)
    private boolean autoRenew = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PremiumTier premiumTier = PremiumTier.BASIC;

    public boolean isMembershipActive() {
        return membershipEndDate == null || LocalDate.now().isBefore(membershipEndDate);
    }
}
