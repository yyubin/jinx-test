package org.jinx.jinxtest.ecommerce.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.jinx.jinxtest.ecommerce.vo.Money;

/**
 * Money VO를 DB에 "amount:currencyCode" 형식 VARCHAR로 저장.
 * 예: "10000.00:KRW"
 */
@Converter
public class MoneyConverter implements AttributeConverter<Money, String> {

    @Override
    public String convertToDatabaseColumn(Money attribute) {
        if (attribute == null) return null;
        return attribute.toDbString();
    }

    @Override
    public Money convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) return null;
        return Money.fromDbString(dbData);
    }
}
