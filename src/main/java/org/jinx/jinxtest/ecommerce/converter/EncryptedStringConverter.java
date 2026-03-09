package org.jinx.jinxtest.ecommerce.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 민감 문자열(카드번호, 주민번호 등)을 AES-128-CBC로 암호화하여 DB 저장.
 * 실 운영에서는 키를 환경변수/KMS로 관리해야 함.
 */
@Converter
public class EncryptedStringConverter implements AttributeConverter<String, String> {

    // 16-byte key (128-bit) — 실 운영에서는 외부 주입
    private static final byte[] SECRET_KEY = "jinxSecretKey123".getBytes(StandardCharsets.UTF_8);
    private static final byte[] INIT_VECTOR = "jinxInitVector16".getBytes(StandardCharsets.UTF_8);
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) return null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE,
                    new SecretKeySpec(SECRET_KEY, "AES"),
                    new IvParameterSpec(INIT_VECTOR));
            byte[] encrypted = cipher.doFinal(attribute.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new IllegalStateException("Encryption failed", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE,
                    new SecretKeySpec(SECRET_KEY, "AES"),
                    new IvParameterSpec(INIT_VECTOR));
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(dbData));
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException("Decryption failed", e);
        }
    }
}
