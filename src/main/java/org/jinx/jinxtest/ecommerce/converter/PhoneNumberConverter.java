package org.jinx.jinxtest.ecommerce.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.jinx.jinxtest.ecommerce.vo.PhoneNumber;

/**
 * PhoneNumber VO를 E.164 포맷 문자열로 DB 저장.
 * 예: "+821012345678"
 */
@Converter
public class PhoneNumberConverter implements AttributeConverter<PhoneNumber, String> {

    @Override
    public String convertToDatabaseColumn(PhoneNumber attribute) {
        if (attribute == null) return null;
        return attribute.toE164();
    }

    @Override
    public PhoneNumber convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) return null;
        return PhoneNumber.fromE164(dbData);
    }
}
