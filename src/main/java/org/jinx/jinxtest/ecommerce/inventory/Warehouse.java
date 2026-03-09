package org.jinx.jinxtest.ecommerce.inventory;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jinx.jinxtest.ecommerce.converter.PhoneNumberConverter;
import org.jinx.jinxtest.ecommerce.vo.Address;
import org.jinx.jinxtest.ecommerce.vo.PhoneNumber;

import java.util.ArrayList;
import java.util.List;

/**
 * 창고 엔티티. Inventory와 1:N 관계.
 * 주소 (Embedded VO), 연락처 (PhoneNumber Converter).
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String warehouseCode;

    @Column(nullable = false, length = 100)
    private String name;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street",     column = @Column(name = "wh_street",      length = 200)),
        @AttributeOverride(name = "city",       column = @Column(name = "wh_city",        length = 100)),
        @AttributeOverride(name = "state",      column = @Column(name = "wh_state",       length = 100)),
        @AttributeOverride(name = "postalCode", column = @Column(name = "wh_postal_code", length = 20)),
        @AttributeOverride(name = "country",    column = @Column(name = "wh_country",     length = 100))
    })
    private Address address;

    @Convert(converter = PhoneNumberConverter.class)
    @Column(name = "contact_phone", length = 20)
    private PhoneNumber contactPhone;

    @Column(nullable = false)
    private double totalCapacitySqm;    // 총 면적 (m²)

    @Column(nullable = false)
    private double usedCapacitySqm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private WarehouseType warehouseType = WarehouseType.STANDARD;

    @Column(nullable = false)
    private boolean isActive = true;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inventory> inventories = new ArrayList<>();
}
