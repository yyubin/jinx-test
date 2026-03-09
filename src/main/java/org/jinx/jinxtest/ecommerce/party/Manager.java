package org.jinx.jinxtest.ecommerce.party;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 매니저 — Party → Person → Employee → Manager (4단계 JOINED).
 * 담당 팀, 예산 권한, 프리미엄 고객 담당.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Manager extends Employee {

    @Column(nullable = false, length = 100)
    private String teamName;

    /** 팀 예산 (별도 Money 컬럼) */
    @Column(name = "team_budget", length = 30)
    private String teamBudgetRaw;   // MoneyConverter 재사용 예시용 raw 보관

    @Column(nullable = false)
    private int maxDirectReports = 10;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ManagerLevel managerLevel = ManagerLevel.TEAM_LEAD;

    /** 담당 프리미엄 고객 목록 (역방향: PremiumCustomer.dedicatedManager) */
    @OneToMany(mappedBy = "dedicatedManager", fetch = FetchType.LAZY)
    private List<PremiumCustomer> managedPremiumCustomers = new ArrayList<>();
}
