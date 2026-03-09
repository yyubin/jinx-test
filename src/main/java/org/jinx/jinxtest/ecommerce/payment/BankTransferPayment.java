package org.jinx.jinxtest.ecommerce.payment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 계좌이체 결제 — TABLE_PER_CLASS로 독립 테이블 bank_transfer_payment.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class BankTransferPayment extends Payment {

    @Column(nullable = false, length = 100)
    private String bankName;

    @Column(nullable = false, length = 50)
    private String bankCode;

    @Column(nullable = false, length = 50)
    private String accountNumber;   // 실제는 마스킹 처리 필요

    @Column(nullable = false, length = 100)
    private String accountHolderName;

    @Column(length = 100)
    private String swiftCode;       // 해외 송금 시 사용

    @Column(length = 100)
    private String ibanNumber;

    @Column(nullable = false)
    private LocalDate expectedTransferDate;

    private LocalDate actualTransferDate;

    @Column(length = 200)
    private String transferNote;

    @Column(nullable = false)
    private boolean isVerified = false;
}
