package com.kadir.expensetracker.util;

public class CategoryUtil {

    /**
     * Kullanıcının girdiği kategoriyi normalize eder.
     * Örn: "gIdA, " → "Gıda"
     *      "   ulaşım" → "Ulaşım"
     *      "KİRA" → "Kira"
     */
    public static String normalize(String input) {
        if (input == null || input.isBlank()) return null;

        // Baştaki/sondaki boşlukları sil, virgül temizle
        String cleaned = input.trim().replace(",", "");

        // Harfler arasındaki tüm boşlukları kaldır (örn: "u l a ş ı m" → "ulaşım")
        cleaned = cleaned.replaceAll("\\s+", "");

        if (cleaned.isEmpty()) return null;

        // İlk harf büyük, kalan küçük
        return cleaned.substring(0, 1).toUpperCase() +
                cleaned.substring(1).toLowerCase();
    }


}
