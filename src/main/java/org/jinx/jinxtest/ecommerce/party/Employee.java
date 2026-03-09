package org.jinx.jinxtest.ecommerce.party;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jinx.jinxtest.ecommerce.converter.EncryptedStringConverter;
import org.jinx.jinxtest.ecommerce.converter.MoneyConverter;
import org.jinx.jinxtest.ecommerce.vo.Money;

import java.time.LocalDate;

/**
 * 직원 — Party → Person → Employee (3단계 JOINED).
 * 급여, 주민번호(암호화) 등 민감 정보 포함.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Employee extends Person {

    @Column(nullable = false, unique = true, length = 20)
    private String employeeNumber;

    @Column(nullable = false, length = 100)
    private String department;

    @Column(nullable = false, length = 100)
    private String position;

    /** 급여 (Money VO → "amount:currency" Converter) */
    @Convert(converter = MoneyConverter.class)
    @Column(name = "salary", length = 30)
    private Money salary;

    @Column(nullable = false)
    private LocalDate hireDate;

    private LocalDate terminationDate;

    /** 주민등록번호 (암호화 Converter) */
    @Convert(converter = EncryptedStringConverter.class)
    @Column(name = "ssn_encrypted", length = 500)
    private String socialSecurityNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EmploymentType employmentType = EmploymentType.FULL_TIME;

    /** 직속 상관 (자기 참조: Employee → Employee) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id")
    private Employee supervisor;

    public boolean isActive() {
        return terminationDate == null || LocalDate.now().isBefore(terminationDate);
    }
}
