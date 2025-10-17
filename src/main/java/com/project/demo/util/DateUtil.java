package com.project.demo.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtil {
    private static final DateTimeFormatter DATETIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss",new Locale("es", "ES"));


    public static LocalDate parseToLocalDate(String value) {
        try {
            if (value == null || value.trim().isEmpty()) return null;

            value = value.replace("\u00A0", "").replace(".", "").trim().toLowerCase();

            // Normalizar abreviaturas del Excel a las que Java reconoce en español
            value = value.replace("set", "sept");  // septiembre
            // ... puedes agregar más si es necesario

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy", new Locale("es", "ES"));
            return LocalDate.parse(value, formatter);
        } catch (Exception e) {
            System.err.println("❌ Error parseando fecha (LocalDate): " + value);
            return null;
        }
    }


    public static LocalDateTime parseToLocalDateTime(String value) {
        try {
            if (value == null || value.trim().isEmpty()) {
                return null;
            }
            return LocalDateTime.parse(value.trim(), DATETIME_FORMATTER);
        } catch (Exception e) {
            System.err.println("❌ Error parseando fecha (LocalDateTime): " + value);
            return null;
        }
    }
}
