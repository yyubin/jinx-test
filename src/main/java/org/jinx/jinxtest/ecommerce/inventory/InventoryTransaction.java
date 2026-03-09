package org.jinx.jinxtest.ecommerce.inventory;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 재고 이동/변경 이력. Inventory와 N:1, 불변 감사 로그 성격.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class InventoryTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TransactionType transactionType;

    /** 변동량 (양수=입고, 음수=출고) */
    @Column(nullable = false)
    private int quantityDelta;

    /** 변경 후 재고 스냅샷 */
    @Column(nullable = false)
    private int quantityAfter;

    @Column(nullable = false, updatable = false)
    private LocalDateTime occurredAt = LocalDateTime.now();

    @Column(length = 100)
    private String referenceId;     // 주문번호, 입고번호 등

    @Column(length = 200)
    private String note;

    @Column(nullable = false, length = 100)
    private String performedBy;     // 처리자 (employee number 등)
}
