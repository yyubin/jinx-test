package org.jinx.jinxtest.ecommerce.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jinx.jinxtest.ecommerce.converter.JsonMapConverter;
import org.jinx.jinxtest.ecommerce.converter.MoneyConverter;
import org.jinx.jinxtest.ecommerce.party.Customer;
import org.jinx.jinxtest.ecommerce.payment.Payment;
import org.jinx.jinxtest.ecommerce.vo.Address;
import org.jinx.jinxtest.ecommerce.vo.Money;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 주문 엔티티. Customer, OrderItem, Payment과 복잡한 연관관계.
 * 배송지 주소 (Embedded), 금액 (Money Converter), 메타데이터 (JSON Converter).
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OrderStatus status = OrderStatus.CART;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    /** 배송지 주소 (Embedded VO) */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street",     column = @Column(name = "ship_street",      length = 200)),
        @AttributeOverride(name = "city",       column = @Column(name = "ship_city",        length = 100)),
        @AttributeOverride(name = "state",      column = @Column(name = "ship_state",       length = 100)),
        @AttributeOverride(name = "postalCode", column = @Column(name = "ship_postal_code", length = 20)),
        @AttributeOverride(name = "country",    column = @Column(name = "ship_country",     length = 100))
    })
    private Address shippingAddress;

    /** 청구지 주소 (Embedded VO, 별도 컬럼) */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street",     column = @Column(name = "bill_street",      length = 200)),
        @AttributeOverride(name = "city",       column = @Column(name = "bill_city",        length = 100)),
        @AttributeOverride(name = "state",      column = @Column(name = "bill_state",       length = 100)),
        @AttributeOverride(name = "postalCode", column = @Column(name = "bill_postal_code", length = 20)),
        @AttributeOverride(name = "country",    column = @Column(name = "bill_country",     length = 100))
    })
    private Address billingAddress;

    /** 소계 (Money Converter) */
    @Convert(converter = MoneyConverter.class)
    @Column(name = "subtotal", length = 30, nullable = false)
    private Money subtotal;

    /** 배송비 (Money Converter) */
    @Convert(converter = MoneyConverter.class)
    @Column(name = "shipping_fee", length = 30)
    private Money shippingFee;

    /** 할인 금액 (Money Converter) */
    @Convert(converter = MoneyConverter.class)
    @Column(name = "discount_amount", length = 30)
    private Money discountAmount;

    /** 최종 결제 금액 (Money Converter) */
    @Convert(converter = MoneyConverter.class)
    @Column(name = "total_amount", length = 30, nullable = false)
    private Money totalAmount;

    @Column(nullable = false, updatable = false)
    private LocalDateTime orderedAt = LocalDateTime.now();

    private LocalDateTime paidAt;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime cancelledAt;

    @Column(length = 50)
    private String couponCode;

    @Column(columnDefinition = "TEXT")
    private String customerNote;

    /** 주문 메타데이터 (JSON Converter) — 장치정보, IP 등 */
    @Convert(converter = JsonMapConverter.class)
    @Column(name = "order_metadata", columnDefinition = "TEXT")
    private Map<String, Object> orderMetadata = new HashMap<>();

    /** 주문 항목 목록 */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    /** 연결된 결제 목록 (재결제, 부분환불 등으로 복수) */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();

    /** 배송 정보 목록 (부분배송 지원) */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Shipment> shipments = new ArrayList<>();
}
