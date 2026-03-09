package org.jinx.jinxtest.ecommerce.payment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jinx.jinxtest.ecommerce.converter.EncryptedStringConverter;

/**
 * 신용카드 결제 — TABLE_PER_CLASS로 독립 테이블 credit_card_payment.
 * 카드번호는 암호화 Converter 사용.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class CreditCardPayment extends Payment {

    /** 마스킹된 카드번호 (예: "****-****-****-1234") */
    @Column(nullable = false, length = 25)
    private String maskedCardNumber;

    /** 카드번호 암호화 저장 (AES Converter) */
    @Convert(converter = EncryptedStringConverter.class)
    @Column(name = "card_number_encrypted", length = 500)
    private String encryptedCardNumber;

    @Column(nullable = false, length = 200)
    private String cardHolderName;

    @Column(nullable = false, length = 4)
    private String expiryMonth;

    @Column(nullable = false, length = 4)
    private String expiryYear;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CardNetwork cardNetwork;

    @Column(nullable = false)
    private boolean threeDsVerified = false;

    @Column(length = 100)
    private String authorizationCode;

    @Column(nullable = false)
    private int installmentMonths = 1;  // 할부 개월 수
}
