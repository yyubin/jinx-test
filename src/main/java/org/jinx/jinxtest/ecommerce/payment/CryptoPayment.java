package org.jinx.jinxtest.ecommerce.payment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 암호화폐 결제 — TABLE_PER_CLASS로 독립 테이블 crypto_payment.
 * 환율, 블록체인 트랜잭션 해시 등 크립토 특화 속성.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class CryptoPayment extends Payment {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CryptoCurrency cryptoCurrency;

    @Column(nullable = false, precision = 20, scale = 8)
    private BigDecimal cryptoAmount;    // 코인 수량

    @Column(nullable = false, precision = 20, scale = 8)
    private BigDecimal exchangeRateAtPayment;   // 결제 시점 환율 (KRW 기준)

    @Column(nullable = false, length = 200)
    private String walletAddress;       // 수신 지갑 주소

    @Column(length = 200)
    private String senderWalletAddress;

    @Column(unique = true, length = 200)
    private String blockchainTxHash;    // 블록체인 트랜잭션 해시

    @Column(nullable = false)
    private int requiredConfirmations = 6;

    @Column(nullable = false)
    private int currentConfirmations = 0;

    @Column(nullable = false, length = 50)
    private String networkName;         // 예: "Ethereum Mainnet", "BSC"

    public boolean isConfirmed() {
        return currentConfirmations >= requiredConfirmations;
    }
}
