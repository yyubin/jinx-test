package org.jinx.jinxtest.ecommerce.vo;

import java.util.Objects;

public class Color {

    private final int r;
    private final int g;
    private final int b;
    private final int alpha;

    public Color(int r, int g, int b, int alpha) {
        this.r = clamp(r);
        this.g = clamp(g);
        this.b = clamp(b);
        this.alpha = clamp(alpha);
    }

    public Color(int r, int g, int b) {
        this(r, g, b, 255);
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }

    public String toHex() {
        return String.format("#%02X%02X%02X%02X", alpha, r, g, b);
    }

    public static Color fromHex(String hex) {
        if (hex == null || hex.isBlank()) return null;
        String cleaned = hex.startsWith("#") ? hex.substring(1) : hex;
        if (cleaned.length() == 6) {
            return new Color(
                Integer.parseInt(cleaned.substring(0, 2), 16),
                Integer.parseInt(cleaned.substring(2, 4), 16),
                Integer.parseInt(cleaned.substring(4, 6), 16)
            );
        } else if (cleaned.length() == 8) {
            return new Color(
                Integer.parseInt(cleaned.substring(2, 4), 16),
                Integer.parseInt(cleaned.substring(4, 6), 16),
                Integer.parseInt(cleaned.substring(6, 8), 16),
                Integer.parseInt(cleaned.substring(0, 2), 16)
            );
        }
        throw new IllegalArgumentException("Invalid hex color: " + hex);
    }

    public int getR() { return r; }
    public int getG() { return g; }
    public int getB() { return b; }
    public int getAlpha() { return alpha; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Color color)) return false;
        return r == color.r && g == color.g && b == color.b && alpha == color.alpha;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b, alpha);
    }

    @Override
    public String toString() {
        return toHex();
    }
}
