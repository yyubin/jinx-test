package org.jinx.jinxtest.ecommerce.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jinx.jinxtest.ecommerce.converter.MoneyConverter;
import org.jinx.jinxtest.ecommerce.vo.Money;

import java.time.LocalDateTime;

/**
 * 배송 정보. 하나의 주문이 여러 번에 나뉘어 배송될 수 있음 (부분 배송).
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false, unique = true, length = 100)
    private String trackingNumber;

    @Column(nullable = false, length = 100)
    private String carrierName;

    @Column(length = 200)
    private String trackingUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ShipmentStatus shipmentStatus = ShipmentStatus.PREPARING;

    @Convert(converter = MoneyConverter.class)
    @Column(name = "shipping_cost", length = 30)
    private Money shippingCost;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime shippedAt;
    private LocalDateTime estimatedDeliveryAt;
    private LocalDateTime actualDeliveryAt;

    @Column(length = 200)
    private String deliveryNote;

    @Column(nullable = false)
    private double weightKg;
}
