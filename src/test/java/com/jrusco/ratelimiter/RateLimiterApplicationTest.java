package com.jrusco.ratelimiter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(properties = {
        "logging.level.org.springframework.web=WARN",
        "logging.level.com.jrusco.ratelimiter=INFO"
})
class RateLimiterApplicationTest {

    @Test
    void contextLoads() {
        assertTrue(true, "Application context should load without errors");
    }
}
