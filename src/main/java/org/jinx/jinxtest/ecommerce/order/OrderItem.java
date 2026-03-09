package org.jinx.jinxtest.ecommerce.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jinx.jinxtest.ecommerce.converter.JsonMapConverter;
import org.jinx.jinxtest.ecommerce.converter.MoneyConverter;
import org.jinx.jinxtest.ecommerce.product.Product;
import org.jinx.jinxtest.ecommerce.vo.Money;

import java.util.HashMap;
import java.util.Map;

/**
 * 주문 항목. 주문 시점의 상품 정보 스냅샷 + 현재 Product 참조 유지.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /** 주문 시점 상품명 스냅샷 */
    @Column(nullable = false, length = 200)
    private String productNameSnapshot;

    /** 주문 시점 SKU 스냅샷 */
    @Column(nullable = false, length = 50)
    private String skuSnapshot;

    /** 주문 시점 단가 스냅샷 (Money Converter) */
    @Convert(converter = MoneyConverter.class)
    @Column(name = "unit_price_snapshot", length = 30, nullable = false)
    private Money unitPriceSnapshot;

    @Column(nullable = false)
    private int quantity;

    /** 항목 할인율 (0.00 ~ 1.00) */
    @Column(nullable = false)
    private double discountRate = 0.0;

    /** 항목 합계 (Money Converter) */
    @Convert(converter = MoneyConverter.class)
    @Column(name = "line_total", length = 30, nullable = false)
    private Money lineTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderItemStatus itemStatus = OrderItemStatus.ORDERED;

    /** 선택된 옵션 (JSON Converter: 사이즈, 색상 등) */
    @Convert(converter = JsonMapConverter.class)
    @Column(name = "selected_options", columnDefinition = "TEXT")
    private Map<String, Object> selectedOptions = new HashMap<>();

    /** 반품 수량 */
    @Column(nullable = false)
    private int returnedQuantity = 0;
}
