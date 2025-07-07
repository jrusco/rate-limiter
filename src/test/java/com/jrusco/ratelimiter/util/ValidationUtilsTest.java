package com.jrusco.ratelimiter.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidationUtilsTest {

    private ValidationUtils validationUtils;

    @BeforeEach
    void setUp() {
        validationUtils = new ValidationUtils();
    }

    @Test
    void testValidIpAddresses() {
        // Valid IPv4 addresses
        assertTrue(validationUtils.isValidIpAddress("192.168.1.1"));
        assertTrue(validationUtils.isValidIpAddress("10.0.0.1"));
        assertTrue(validationUtils.isValidIpAddress("127.0.0.1"));
        assertTrue(validationUtils.isValidIpAddress("255.255.255.255"));

        // Valid IPv6 addresses
        assertTrue(validationUtils.isValidIpAddress("::1"));
        assertTrue(validationUtils.isValidIpAddress("2001:db8::1"));
    }

    @Test
    void testInvalidIpAddresses() {
        // Invalid IPv4 addresses
        assertFalse(validationUtils.isValidIpAddress("256.256.256.256"));
        assertFalse(validationUtils.isValidIpAddress("192.168.1"));
        assertFalse(validationUtils.isValidIpAddress("192.168.1.1.1"));
        assertFalse(validationUtils.isValidIpAddress("invalid"));
        assertFalse(validationUtils.isValidIpAddress(""));
        assertFalse(validationUtils.isValidIpAddress(null));
    }

    @Test
    void testValidUserIds() {
        assertTrue(validationUtils.isValidUserId("user123"));
        assertTrue(validationUtils.isValidUserId("user_123"));
        assertTrue(validationUtils.isValidUserId("user-123"));
        assertTrue(validationUtils.isValidUserId("user.123"));
        assertTrue(validationUtils.isValidUserId("User123"));
    }

    @Test
    void testInvalidUserIds() {
        assertFalse(validationUtils.isValidUserId(""));
        assertFalse(validationUtils.isValidUserId(null));
        assertFalse(validationUtils.isValidUserId("user@123"));
        assertFalse(validationUtils.isValidUserId("user 123"));
        assertFalse(validationUtils.isValidUserId("user#123"));

        // Test length limit
        String longUserId = "a".repeat(256);
        assertFalse(validationUtils.isValidUserId(longUserId));
    }

    @Test
    void testValidApiKeys() {
        assertTrue(validationUtils.isValidApiKey("key123"));
        assertTrue(validationUtils.isValidApiKey("key_123"));
        assertTrue(validationUtils.isValidApiKey("key-123"));
        assertTrue(validationUtils.isValidApiKey("key.123"));
        assertTrue(validationUtils.isValidApiKey("Key123"));
    }

    @Test
    void testInvalidApiKeys() {
        assertFalse(validationUtils.isValidApiKey(""));
        assertFalse(validationUtils.isValidApiKey(null));
        assertFalse(validationUtils.isValidApiKey("key@123"));
        assertFalse(validationUtils.isValidApiKey("key 123"));
        assertFalse(validationUtils.isValidApiKey("key#123"));

        // Test length limit
        String longApiKey = "a".repeat(129);
        assertFalse(validationUtils.isValidApiKey(longApiKey));
    }

    @Test
    void testValidEndpoints() {
        assertTrue(validationUtils.isValidEndpoint("/api/v1"));
        assertTrue(validationUtils.isValidEndpoint("/api/v1/users"));
        assertTrue(validationUtils.isValidEndpoint("/api-v1/users"));
        assertTrue(validationUtils.isValidEndpoint("/api_v1/users"));
        assertTrue(validationUtils.isValidEndpoint("/api.v1/users"));
    }

    @Test
    void testInvalidEndpoints() {
        assertFalse(validationUtils.isValidEndpoint(""));
        assertFalse(validationUtils.isValidEndpoint(null));
        assertFalse(validationUtils.isValidEndpoint("api/v1")); // Must start with /
        assertFalse(validationUtils.isValidEndpoint("/api@v1"));
        assertFalse(validationUtils.isValidEndpoint("/api v1"));

        // Test length limit
        String longEndpoint = "/api/" + "a".repeat(250);
        assertFalse(validationUtils.isValidEndpoint(longEndpoint));
    }

    @Test
    void testIsNotNullOrEmpty() {
        assertTrue(validationUtils.isNotNullOrEmpty("test"));
        assertTrue(validationUtils.isNotNullOrEmpty("  test  "));
        assertFalse(validationUtils.isNotNullOrEmpty(""));
        assertFalse(validationUtils.isNotNullOrEmpty("   "));
        assertFalse(validationUtils.isNotNullOrEmpty(null));
    }

    @Test
    void testIsValidLength() {
        assertTrue(validationUtils.isValidLength("test", 10));
        assertTrue(validationUtils.isValidLength("test", 4));
        assertFalse(validationUtils.isValidLength("test", 3));
        assertFalse(validationUtils.isValidLength(null, 10));
    }

    @Test
    void testSanitizeString() {
        assertEquals("test", validationUtils.sanitizeString("test"));
        assertEquals("test", validationUtils.sanitizeString("test<>"));
        assertEquals("test", validationUtils.sanitizeString("test\"'&"));
        assertEquals("test", validationUtils.sanitizeString("  test  "));
        assertNull(validationUtils.sanitizeString(null));
    }

    @Test
    void testExtractRealClientIp() {
        // Test X-Forwarded-For
        assertEquals("1.2.3.4", validationUtils.extractRealClientIp("1.2.3.4", null, "127.0.0.1"));
        assertEquals("1.2.3.4", validationUtils.extractRealClientIp("1.2.3.4, 192.168.1.1", null, "127.0.0.1"));

        // Test X-Real-IP fallback
        assertEquals("1.2.3.4", validationUtils.extractRealClientIp(null, "1.2.3.4", "127.0.0.1"));

        // Test remote address fallback
        assertEquals("1.2.3.4", validationUtils.extractRealClientIp(null, null, "1.2.3.4"));

        // Test unknown fallback
        assertEquals("unknown", validationUtils.extractRealClientIp(null, null, null));
    }
}
