package org.jinx.jinxtest.ecommerce.party;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jinx.jinxtest.ecommerce.converter.PhoneNumberConverter;
import org.jinx.jinxtest.ecommerce.vo.PhoneNumber;

/**
 * 조직(법인) Party. JOINED 상속으로 party + organization 테이블 join.
 * Vendor / Supplier로 분기.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public abstract class Organization extends Party {

    @Column(nullable = false, unique = true, length = 50)
    private String businessRegistrationNumber;

    @Column(nullable = false, length = 200)
    private String legalName;

    @Column(length = 200)
    private String website;

    @Convert(converter = PhoneNumberConverter.class)
    @Column(name = "representative_phone", length = 20)
    private PhoneNumber representativePhone;

    @Column(length = 200)
    private String ceoName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OrganizationType organizationType;

    /** 모기업 (자기 참조: Organization → Organization) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_org_id")
    private Organization parentOrganization;
}
