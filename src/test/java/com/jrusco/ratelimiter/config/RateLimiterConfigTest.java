package com.jrusco.ratelimiter.config;

import com.jrusco.ratelimiter.enums.RateLimitAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestPropertySource(properties = {
        "rate-limiter.default-limits.requests-per-minute=200",
        "rate-limiter.default-limits.burst-size=20",
        "rate-limiter.algorithms.default-algorithm=FIXED_WINDOW"
})
class RateLimiterConfigTest {

    @Autowired
    private RateLimiterConfig rateLimiterConfig;

    @Test
    void testConfigurationLoading() {
        assertNotNull(rateLimiterConfig);
        assertNotNull(rateLimiterConfig.getDefaultLimits());
        assertNotNull(rateLimiterConfig.getAlgorithms());
        assertNotNull(rateLimiterConfig.getSecurity());
        assertNotNull(rateLimiterConfig.getLogging());
    }

    @Test
    void testDefaultLimits() {
        RateLimiterConfig.DefaultLimits limits = rateLimiterConfig.getDefaultLimits();
        assertEquals(200, limits.getRequestsPerMinute());
        assertEquals(20, limits.getBurstSize());
    }

    @Test
    void testAlgorithmConfiguration() {
        RateLimiterConfig.Algorithms algorithms = rateLimiterConfig.getAlgorithms();
        assertEquals(RateLimitAlgorithm.FIXED_WINDOW, algorithms.getDefaultAlgorithm());
    }

    @Test
    void testSecurityConfiguration() {
        RateLimiterConfig.Security security = rateLimiterConfig.getSecurity();
        assertNotNull(security.getTrustedHeaders());
        assertFalse(security.getTrustedHeaders().isEmpty());
        assertTrue(security.getMaxUserIdLength() > 0);
        assertTrue(security.getMaxApiKeyLength() > 0);
    }

    @Test
    void testLoggingConfiguration() {
        RateLimiterConfig.Logging logging = rateLimiterConfig.getLogging();
        assertNotNull(logging);
        // Test default values are properly set
        assertTrue(logging.isLogDeniedRequests());
        assertTrue(logging.isLogConfigurationChanges());
    }
}
