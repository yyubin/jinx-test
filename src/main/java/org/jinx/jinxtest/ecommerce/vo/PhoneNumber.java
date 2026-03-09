package org.jinx.jinxtest.ecommerce.vo;

import java.util.Objects;
import java.util.regex.Pattern;

public class PhoneNumber {

    private static final Pattern E164_PATTERN = Pattern.compile("^\\+[1-9]\\d{1,14}$");

    private final String countryCode;
    private final String nationalNumber;

    public PhoneNumber(String countryCode, String nationalNumber) {
        this.countryCode = countryCode.startsWith("+") ? countryCode : "+" + countryCode;
        this.nationalNumber = nationalNumber.replaceAll("[\\s\\-()]", "");
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getNationalNumber() {
        return nationalNumber;
    }

    public String toE164() {
        return countryCode + nationalNumber;
    }

    public static PhoneNumber fromE164(String e164) {
        if (e164 == null || e164.isBlank()) return null;
        // naive split: "+82" + rest
        if (e164.startsWith("+82")) return new PhoneNumber("+82", e164.substring(3));
        if (e164.startsWith("+1"))  return new PhoneNumber("+1",  e164.substring(2));
        // generic fallback: first 3 chars = country code
        return new PhoneNumber(e164.substring(0, 3), e164.substring(3));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneNumber that)) return false;
        return Objects.equals(countryCode, that.countryCode) &&
               Objects.equals(nationalNumber, that.nationalNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode, nationalNumber);
    }

    @Override
    public String toString() {
        return countryCode + " " + nationalNumber;
    }
}
