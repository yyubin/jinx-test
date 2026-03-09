package org.jinx.jinxtest.ecommerce.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jinx.jinxtest.ecommerce.converter.CommaSeparatedListConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 디지털 상품 — SINGLE_TABLE 2단계 (Product → DigitalProduct).
 * 일반 디지털 상품 (SoftwareLicense, DownloadableMedia와 별개 타입).
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class DigitalProduct extends Product {

    private double fileSizeMb;

    @Convert(converter = CommaSeparatedListConverter.class)
    @Column(name = "digital_file_formats", length = 200)
    private List<String> fileFormats = new ArrayList<>();

    @Column(name = "digital_has_drm", nullable = false)
    private boolean hasDrm = false;

    @Column(name = "digital_max_downloads", nullable = false)
    private int maxDownloads = 0;

    @Column(length = 200)
    private String downloadUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "digital_delivery_method", nullable = false, length = 20)
    private DigitalDeliveryMethod deliveryMethod = DigitalDeliveryMethod.IMMEDIATE;
}
