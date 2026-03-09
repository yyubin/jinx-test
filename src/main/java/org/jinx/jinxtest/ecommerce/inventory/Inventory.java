package org.jinx.jinxtest.ecommerce.inventory;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jinx.jinxtest.ecommerce.party.Supplier;
import org.jinx.jinxtest.ecommerce.product.Product;

import java.time.LocalDateTime;

/**
 * 재고 엔티티. Optimistic Locking(@Version)으로 동시성 제어.
 * Product, Supplier와 연관. 재고 이력(InventoryTransaction)과 1:N.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(nullable = false)
    private int quantityOnHand;     // 실재고

    @Column(nullable = false)
    private int quantityReserved;   // 주문 확정 후 배송 전 예약 수량

    @Column(nullable = false)
    private int quantityOnOrder;    // 공급자에게 발주된 수량

    @Column(nullable = false)
    private int reorderPoint;       // 재발주 기준점

    @Column(nullable = false)
    private int reorderQuantity;    // 재발주 수량

    @Column(nullable = false)
    private int safetyStock;        // 안전 재고

    /** Optimistic Locking — 동시 재고 차감 충돌 방지 */
    @Version
    private Long version;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public int getAvailableQuantity() {
        return quantityOnHand - quantityReserved;
    }

    public boolean needsReorder() {
        return getAvailableQuantity() <= reorderPoint;
    }
}
