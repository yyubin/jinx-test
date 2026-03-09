package org.jinx.jinxtest.ecommerce.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jinx.jinxtest.ecommerce.converter.CommaSeparatedListConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 소프트웨어 라이선스 — SINGLE_TABLE 2단계 (Product → SoftwareLicense).
 * 디지털 공통 속성 + 라이선스 특화 속성 인라인.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class SoftwareLicense extends Product {

    // ── DigitalProduct 공통 속성 ──
    private double fileSizeMb;

    @Convert(converter = CommaSeparatedListConverter.class)
    @Column(name = "file_formats", length = 200)
    private List<String> fileFormats = new ArrayList<>();

    @Column(nullable = false)
    private boolean hasDrm = false;

    @Column(nullable = false)
    private int maxDownloads = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DigitalDeliveryMethod deliveryMethod = DigitalDeliveryMethod.IMMEDIATE;

    // ── SoftwareLicense 특화 속성 ──
    @Column(length = 100)
    private String softwareName;

    @Column(length = 50)
    private String version;

    @Column(nullable = false)
    private int maxInstallations = 1;

    @Column(nullable = false)
    private int validityDays = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "license_type", length = 20)
    private LicenseType licenseType = LicenseType.SINGLE_USER;

    @Column(nullable = false)
    private boolean isSubscriptionBased = false;

    @Column(length = 200)
    private String activationInstructions;

    @Column(nullable = false)
    private boolean supportsOfflineMode = true;
}
