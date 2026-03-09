package org.jinx.jinxtest.ecommerce.review;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jinx.jinxtest.ecommerce.converter.CommaSeparatedListConverter;
import org.jinx.jinxtest.ecommerce.order.OrderItem;
import org.jinx.jinxtest.ecommerce.party.Customer;
import org.jinx.jinxtest.ecommerce.product.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 상품 리뷰. Customer, Product, OrderItem과 연관.
 * 구매 인증 리뷰 / 미인증 리뷰 구분. 이미지 첨부 (콤마 Converter).
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    /** 구매 인증 리뷰: 어떤 OrderItem에서 왔는지 연결 */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @Column(nullable = false)
    private int rating;     // 1 ~ 5

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    /** 첨부 이미지 URL 목록 (콤마 Converter) */
    @Convert(converter = CommaSeparatedListConverter.class)
    @Column(name = "image_urls", length = 2000)
    private List<String> imageUrls = new ArrayList<>();

    @Column(nullable = false)
    private boolean isVerifiedPurchase = false;

    @Column(nullable = false)
    private boolean isVisible = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    /** 도움됨 수 */
    @Column(nullable = false)
    private int helpfulCount = 0;

    /** 신고 수 */
    @Column(nullable = false)
    private int reportCount = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReviewStatus status = ReviewStatus.PENDING_MODERATION;

    /** 대댓글 (자기 참조: 판매자 답글 등) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_review_id")
    private Review parentReview;

    @OneToMany(mappedBy = "parentReview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> replies = new ArrayList<>();

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
