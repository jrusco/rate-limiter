package com.jrusco.ratelimiter.enums;

public enum RateLimitAlgorithm {
    /**
     * Token Bucket algorithm - allows bursts within limits
     */
    TOKEN_BUCKET,

    /**
     * Fixed Window Counter - resets count at fixed intervals
     */
    FIXED_WINDOW,

    /**
     * Sliding Window Log - maintains log of request timestamps
     */
    SLIDING_WINDOW
}
