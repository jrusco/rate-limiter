package com.jrusco.ratelimiter.util;

public class ParsingUtils {

    private ParsingUtils() {
    }

    /**
     * Escapes a string value for JSON format.
     * Handles null values and escapes special characters.
     *
     * @param value the string value to escape
     * @return the escaped JSON string or "null" if the input is null
     */
    public static String escapeJsonString(String value) {
        if (value == null) {
            return "null";
        }
        return "\"" + value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t") + "\"";
    }
}
