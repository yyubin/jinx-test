package org.jinx.jinxtest.ecommerce.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.jinx.jinxtest.ecommerce.vo.Color;

/**
 * Color VO를 ARGB Hex 문자열로 DB 저장.
 * 예: "#FF1A2B3C"
 */
@Converter
public class ColorConverter implements AttributeConverter<Color, String> {

    @Override
    public String convertToDatabaseColumn(Color attribute) {
        if (attribute == null) return null;
        return attribute.toHex();
    }

    @Override
    public Color convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) return null;
        return Color.fromHex(dbData);
    }
}
