package org.jinx.jinxtest.ecommerce.party;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jinx.jinxtest.ecommerce.converter.PhoneNumberConverter;
import org.jinx.jinxtest.ecommerce.vo.PhoneNumber;

import java.time.LocalDate;

/**
 * 개인(자연인) Party. JOINED 상속으로 party + person 테이블 join.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public abstract class Person extends Party {

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(unique = true, length = 320)
    private String email;

    /** PhoneNumber VO → E.164 문자열 Converter */
    @Convert(converter = PhoneNumberConverter.class)
    @Column(name = "phone", length = 20)
    private PhoneNumber phone;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    @Column(columnDefinition = "TEXT")
    private String bio;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
