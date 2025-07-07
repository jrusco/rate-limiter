package com.jrusco.ratelimiter.controller;

import com.jrusco.ratelimiter.dto.RateLimitCheckRequest;
import com.jrusco.ratelimiter.dto.RateLimitCheckResponse;
import com.jrusco.ratelimiter.dto.RateLimitConfigDto;
import com.jrusco.ratelimiter.service.RateLimiterService;
import com.jrusco.ratelimiter.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Map;

/**
 * REST Controller for rate limiting operations.
 */
@RestController
@RequestMapping("/rate_limit")
@Validated
public class RateLimiterController {

    private static final Logger logger = LoggerFactory.getLogger(RateLimiterController.class);

    @Autowired
    private RateLimiterService rateLimiterService;

    /**
     * Check if a request is allowed based on rate limiting rules.
     */
    @PostMapping("/check")
    public ResponseEntity<RateLimitCheckResponse> checkRateLimit(
            @Valid @RequestBody RateLimitCheckRequest request,
            HttpServletRequest servletRequest) {

        logger.debug("{} - msg=[Rate limit check requested], request=[{}]",
                Constants.LOG_PREFIX, request);

        // Extract client IP if not provided
        if (request.getClientIp() == null || request.getClientIp().isEmpty()) {
            String clientIp = extractClientIp(servletRequest);
            request.setClientIp(clientIp);
        }

        // Perform rate limit check (stub implementation)
        RateLimitCheckResponse response = rateLimiterService.checkRateLimit(request);

        // Set response headers
        HttpStatus status = response.isAllowed() ? HttpStatus.OK : HttpStatus.TOO_MANY_REQUESTS;

        logger.info("{} - msg=[Rate limit check completed], identifier=[{}], allowed=[{}]",
                Constants.LOG_PREFIX, request.getIdentifier(), response.isAllowed());

        return new ResponseEntity<>(response, status);
    }

    /**
     * Get current rate limit configuration.
     */
    @GetMapping("/config")
    public ResponseEntity<RateLimitConfigDto> getConfig() {

        logger.debug("{} - msg=[Rate limit configuration requested]", Constants.LOG_PREFIX);

        RateLimitConfigDto config = rateLimiterService.getConfiguration();

        logger.info("{} - msg=[Rate limit configuration retrieved]", Constants.LOG_PREFIX);

        return ResponseEntity.ok(config);
    }

    /**
     * Update rate limit configuration.
     */
    @PostMapping("/config")
    public ResponseEntity<Map<String, String>> updateConfig(
            @Valid @RequestBody RateLimitConfigDto config) {

        logger.debug("{} - msg=[Rate limit configuration update requested], config=[{}]",
                Constants.LOG_PREFIX, config);

        rateLimiterService.updateConfiguration(config);

        logger.info("{} - msg=[Rate limit configuration updated], config=[{}]",
                Constants.LOG_PREFIX, config);

        return ResponseEntity.ok(Map.of("message", Constants.MSG_CONFIGURATION_UPDATED));
    }

    /**
     * Health check endpoint.
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "rate-limiter",
                "timestamp", String.valueOf(System.currentTimeMillis())));
    }

    /**
     * Extract client IP from request headers and remote address.
     */
    private String extractClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader(Constants.HEADER_X_FORWARDED_FOR);
        String xRealIp = request.getHeader(Constants.HEADER_X_REAL_IP);
        String remoteAddr = request.getRemoteAddr();

        // Use validation utility to extract real IP
        return rateLimiterService.extractRealClientIp(xForwardedFor, xRealIp, remoteAddr);
    }
}
