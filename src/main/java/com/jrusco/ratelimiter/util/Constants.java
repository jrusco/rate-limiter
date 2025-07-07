package com.jrusco.ratelimiter.util;

public final class Constants {

    // HTTP Headers
    public static final String HEADER_RATE_LIMIT_LIMIT = "X-RateLimit-Limit";
    public static final String HEADER_RATE_LIMIT_REMAINING = "X-RateLimit-Remaining";
    public static final String HEADER_RATE_LIMIT_RESET = "X-RateLimit-Reset";
    public static final String HEADER_RETRY_AFTER = "Retry-After";
    public static final String HEADER_CORRELATION_ID = "X-Correlation-ID";

    // Request Headers
    public static final String HEADER_X_FORWARDED_FOR = "X-Forwarded-For";
    public static final String HEADER_X_REAL_IP = "X-Real-IP";
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String HEADER_API_KEY = "X-API-Key";

    // Log Messages
    public static final String LOG_PREFIX = "RateLimiter";

    // Response Messages
    public static final String MSG_REQUEST_ALLOWED = "Request allowed";
    public static final String MSG_RATE_LIMIT_EXCEEDED = "Rate limit exceeded";
    public static final String MSG_INVALID_REQUEST = "Invalid request";
    public static final String MSG_CONFIGURATION_UPDATED = "Configuration updated successfully";
    public static final String MSG_CONFIGURATION_RETRIEVED = "Configuration retrieved successfully";

    // Validation Messages
    public static final String VALIDATION_INVALID_IP = "Invalid IP address format";
    public static final String VALIDATION_INVALID_USER_ID = "Invalid user ID format";
    public static final String VALIDATION_INVALID_API_KEY = "Invalid API key format";
    public static final String VALIDATION_REQUIRED_FIELD = "Required field is missing";

    // Time Constants
    public static final long MILLIS_PER_MINUTE = 60_000L;
    public static final long MILLIS_PER_SECOND = 1_000L;

    // Default Values
    public static final int DEFAULT_REQUESTS_PER_MINUTE = 100;
    public static final int DEFAULT_BURST_SIZE = 10;

    private Constants() {
        // Utility class, prevent instantiation
    }
}
