package org.jinx.jinxtest.ecommerce.party;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jinx.jinxtest.ecommerce.converter.JsonMapConverter;
import org.jinx.jinxtest.ecommerce.converter.CommaSeparatedListConverter;
import org.jinx.jinxtest.ecommerce.vo.Address;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 최상위 Party 엔티티. JOINED 전략으로 Person / Organization 분기.
 * Party는 사람(개인)과 조직(법인) 모두를 추상화하는 DDD 패턴.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)

@Getter
@Setter
@NoArgsConstructor
public abstract class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PartyStatus status = PartyStatus.ACTIVE;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    /** 주소 (Embedded VO) */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street",     column = @Column(name = "addr_street",      length = 200)),
        @AttributeOverride(name = "city",       column = @Column(name = "addr_city",        length = 100)),
        @AttributeOverride(name = "state",      column = @Column(name = "addr_state",       length = 100)),
        @AttributeOverride(name = "postalCode", column = @Column(name = "addr_postal_code", length = 20)),
        @AttributeOverride(name = "country",    column = @Column(name = "addr_country",     length = 100))
    })
    private Address address;

    /** 태그 목록 (콤마 구분 Converter) */
    @Convert(converter = CommaSeparatedListConverter.class)
    @Column(name = "tags", length = 500)
    private List<String> tags = new ArrayList<>();

    /** 확장 속성 (JSON Converter) */
    @Convert(converter = JsonMapConverter.class)
    @Column(name = "extra_attributes", columnDefinition = "TEXT")
    private Map<String, Object> extraAttributes = new HashMap<>();

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
