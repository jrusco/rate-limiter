package com.jrusco.ratelimiter.util;

import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

@Component
public class ValidationUtils {

    // User ID pattern - alphanumeric, dash, underscore, dot
    private static final Pattern USER_ID_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._-]+$");

    // API Key pattern - alphanumeric and special characters
    private static final Pattern API_KEY_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._-]+$");

    // Endpoint pattern - valid URL path
    private static final Pattern ENDPOINT_PATTERN = Pattern.compile(
            "^/[a-zA-Z0-9._/-]*$");

    /**
     * Validates if the given string is a valid IP address (IPv4 or IPv6).
     *
     * @param ip the IP address to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidIpAddress(String ip) {
        if (ip == null || ip.trim().isEmpty()) {
            return false;
        }

        // Try parsing with InetAddress (most reliable)
        try {
            InetAddress.getByName(ip);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }

    /**
     * Validates if the given string is a valid user ID.
     *
     * @param userId the user ID to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return false;
        }

        // Check length
        if (userId.length() > 255) {
            return false;
        }

        // Check pattern
        return USER_ID_PATTERN.matcher(userId).matches();
    }

    /**
     * Validates if the given string is a valid API key.
     *
     * @param apiKey the API key to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidApiKey(String apiKey) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            return false;
        }

        // Check length
        if (apiKey.length() > 128) {
            return false;
        }

        // Check pattern
        return API_KEY_PATTERN.matcher(apiKey).matches();
    }

    /**
     * Validates if the given string is a valid endpoint path.
     *
     * @param endpoint the endpoint to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidEndpoint(String endpoint) {
        if (endpoint == null || endpoint.trim().isEmpty()) {
            return false;
        }

        // Check length
        if (endpoint.length() > 255) {
            return false;
        }

        // Check pattern
        return ENDPOINT_PATTERN.matcher(endpoint).matches();
    }

    /**
     * Validates if the given string is not null and not empty.
     *
     * @param value the value to validate
     * @return true if valid, false otherwise
     */
    public boolean isNotNullOrEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * Validates if the given string is within the specified length limit.
     *
     * @param value     the value to validate
     * @param maxLength the maximum allowed length
     * @return true if valid, false otherwise
     */
    public boolean isValidLength(String value, int maxLength) {
        if (value == null) {
            return false;
        }
        return value.length() <= maxLength;
    }

    /**
     * Sanitizes a string by removing potentially dangerous characters.
     *
     * @param input the input string to sanitize
     * @return the sanitized string
     */
    public String sanitizeString(String input) {
        if (input == null) {
            return null;
        }

        // Remove control characters and potential injection attempts
        return input.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "")
                .replaceAll("[<>\"'&]", "")
                .trim();
    }

    /**
     * Extracts the real client IP from request headers, considering proxies.
     *
     * @param xForwardedFor the X-Forwarded-For header value
     * @param xRealIp       the X-Real-IP header value
     * @param remoteAddr    the remote address from the request
     * @return the best guess for the real client IP
     */
    public String extractRealClientIp(String xForwardedFor, String xRealIp, String remoteAddr) {
        // Try X-Forwarded-For first (can contain multiple IPs)
        if (xForwardedFor != null && !xForwardedFor.trim().isEmpty()) {
            String[] ips = xForwardedFor.split(",");
            for (String ip : ips) {
                String trimmedIp = ip.trim();
                if (isValidIpAddress(trimmedIp) && !isPrivateIp(trimmedIp)) {
                    return trimmedIp;
                }
            }
        }

        // Try X-Real-IP
        if (xRealIp != null && !xRealIp.trim().isEmpty() && isValidIpAddress(xRealIp)) {
            return xRealIp;
        }

        // Fall back to remote address
        if (remoteAddr != null && isValidIpAddress(remoteAddr)) {
            return remoteAddr;
        }

        return "unknown";
    }

    /**
     * Checks if an IP address is private (RFC 1918).
     *
     * @param ip the IP address to check
     * @return true if private, false otherwise
     */
    private boolean isPrivateIp(String ip) {
        if (!isValidIpAddress(ip)) {
            return false;
        }

        // Simple check for common private IP ranges
        return ip.startsWith("10.") ||
                ip.startsWith("192.168.") ||
                ip.startsWith("172.16.") ||
                ip.startsWith("172.17.") ||
                ip.startsWith("172.18.") ||
                ip.startsWith("172.19.") ||
                ip.startsWith("172.20.") ||
                ip.startsWith("172.21.") ||
                ip.startsWith("172.22.") ||
                ip.startsWith("172.23.") ||
                ip.startsWith("172.24.") ||
                ip.startsWith("172.25.") ||
                ip.startsWith("172.26.") ||
                ip.startsWith("172.27.") ||
                ip.startsWith("172.28.") ||
                ip.startsWith("172.29.") ||
                ip.startsWith("172.30.") ||
                ip.startsWith("172.31.") ||
                ip.equals("127.0.0.1") ||
                ip.equals("localhost");
    }
}
