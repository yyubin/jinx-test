package org.jinx.jinxtest.ecommerce.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 카테고리 — 자기 참조 트리 구조 (Closure Table 미사용, 단순 parent-child).
 * 카테고리는 별도 Product 상속 계층과 다른 독립 엔티티.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 200)
    private String description;

    @Column(nullable = false)
    private int sortOrder = 0;

    @Column(nullable = false)
    private boolean isActive = true;

    /** 부모 카테고리 (자기 참조 ManyToOne) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    /** 자식 카테고리 목록 (자기 참조 OneToMany) */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> children = new ArrayList<>();

    /** 이 카테고리에 속한 상품 목록 */
    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }
}
