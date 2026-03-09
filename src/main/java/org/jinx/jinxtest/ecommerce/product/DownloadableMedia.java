package org.jinx.jinxtest.ecommerce.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jinx.jinxtest.ecommerce.converter.CommaSeparatedListConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 다운로드 미디어 — SINGLE_TABLE 2단계 (Product → DownloadableMedia).
 * 디지털 공통 속성 + 미디어 콘텐츠 특화 속성 인라인.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class DownloadableMedia extends Product {

    // ── DigitalProduct 공통 속성 ──
    private double fileSizeMb;

    @Convert(converter = CommaSeparatedListConverter.class)
    @Column(name = "media_file_formats", length = 200)
    private List<String> fileFormats = new ArrayList<>();

    @Column(name = "media_has_drm", nullable = false)
    private boolean hasDrm = false;

    @Column(name = "media_max_downloads", nullable = false)
    private int maxDownloads = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_delivery_method", nullable = false, length = 20)
    private DigitalDeliveryMethod deliveryMethod = DigitalDeliveryMethod.IMMEDIATE;

    // ── DownloadableMedia 특화 속성 ──
    @Enumerated(EnumType.STRING)
    @Column(name = "media_type", length = 20)
    private MediaType mediaType;

    @Column(length = 200)
    private String artist;

    @Column(length = 200)
    private String publisher;

    private Integer durationSeconds;
    private Integer pageCount;

    @Column(length = 10)
    private String language;

    @Column(length = 50)
    private String isbn;

    @Column(nullable = false)
    private boolean isExplicitContent = false;

    @Column(length = 20)
    private String ageRating;
}
