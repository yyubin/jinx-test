package org.jinx.jinxtest.ecommerce.party;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jinx.jinxtest.ecommerce.order.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 고객 — Person → Customer (3단계).
 * 주문 이력, 포인트, 등급 관리.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer extends Person {

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private int loyaltyPoints = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CustomerGrade grade = CustomerGrade.BRONZE;

    @Column(nullable = false, updatable = false)
    private LocalDateTime registeredAt = LocalDateTime.now();

    private LocalDateTime lastLoginAt;

    @Column(nullable = false)
    private boolean marketingConsent = false;

    /** 고객의 주문 이력 */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    /** 위시리스트 (자기 참조 M:N 대신 단순 product id 저장) */
    @ElementCollection
    @CollectionTable(name = "customer_wishlist", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "product_id")
    private List<Long> wishlistProductIds = new ArrayList<>();

    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
        recalculateGrade();
    }

    private void recalculateGrade() {
        if (loyaltyPoints >= 100_000) grade = CustomerGrade.DIAMOND;
        else if (loyaltyPoints >= 50_000) grade = CustomerGrade.PLATINUM;
        else if (loyaltyPoints >= 10_000) grade = CustomerGrade.GOLD;
        else if (loyaltyPoints >= 1_000)  grade = CustomerGrade.SILVER;
        else                              grade = CustomerGrade.BRONZE;
    }
}
