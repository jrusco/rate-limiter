package com.jrusco.ratelimiter.exception;

/**
 * Exception thrown when rate limit is exceeded.
 */
public class RateLimitExceededException extends RuntimeException {

    private final String identifier;
    private final long retryAfter;

    public RateLimitExceededException(String message) {
        super(message);
        this.identifier = null;
        this.retryAfter = 0;
    }

    public RateLimitExceededException(String message, String identifier, long retryAfter) {
        super(message);
        this.identifier = identifier;
        this.retryAfter = retryAfter;
    }

    public RateLimitExceededException(String message, Throwable cause) {
        super(message, cause);
        this.identifier = null;
        this.retryAfter = 0;
    }

    public String getIdentifier() {
        return identifier;
    }

    public long getRetryAfter() {
        return retryAfter;
    }
}
