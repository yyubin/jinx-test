package org.jinx.jinxtest.ecommerce.payment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jinx.jinxtest.ecommerce.converter.MoneyConverter;
import org.jinx.jinxtest.ecommerce.order.Order;
import org.jinx.jinxtest.ecommerce.vo.Money;

import java.time.LocalDateTime;

/**
 * 결제 최상위 엔티티. TABLE_PER_CLASS 전략으로 각 서브타입이 독립 테이블 보유.
 * (credit_card_payment, bank_transfer_payment, crypto_payment)
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public abstract class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String transactionId;

    /** 결제 금액 (Money Converter) */
    @Convert(converter = MoneyConverter.class)
    @Column(name = "amount", length = 30, nullable = false)
    private Money amount;

    /** 환불 금액 (Money Converter) */
    @Convert(converter = MoneyConverter.class)
    @Column(name = "refunded_amount", length = 30)
    private Money refundedAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(nullable = false, updatable = false)
    private LocalDateTime initiatedAt = LocalDateTime.now();

    private LocalDateTime completedAt;
    private LocalDateTime refundedAt;

    /** 연결된 주문 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(length = 500)
    private String failureReason;

    @Column(length = 100)
    private String gatewayReference;

    public boolean isSuccessful() {
        return status == PaymentStatus.COMPLETED;
    }

    public boolean isRefundable() {
        return status == PaymentStatus.COMPLETED || status == PaymentStatus.PARTIALLY_REFUNDED;
    }
}
