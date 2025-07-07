package com.jrusco.ratelimiter.service;

import com.jrusco.ratelimiter.config.RateLimiterConfig;
import com.jrusco.ratelimiter.dto.RateLimitCheckRequest;
import com.jrusco.ratelimiter.dto.RateLimitCheckResponse;
import com.jrusco.ratelimiter.dto.RateLimitConfigDto;
import com.jrusco.ratelimiter.util.Constants;
import com.jrusco.ratelimiter.util.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

    private static final Logger logger = LoggerFactory.getLogger(RateLimiterService.class);

    @Autowired
    private RateLimiterConfig rateLimiterConfig;

    @Autowired
    private ValidationUtils validationUtils;

    /**
     * Check if a request is allowed based on rate limiting rules.
     * This is a stub implementation for Milestone 1.
     */
    public RateLimitCheckResponse checkRateLimit(RateLimitCheckRequest request) {
        logger.debug("{} - msg=[Checking rate limit], identifier=[{}], type=[{}]",
                Constants.LOG_PREFIX, request.getIdentifier(), request.getType());

        // Validate request
        validateRequest(request);

        // Stub implementation - always allow for now
        // In Milestone 2, this will contain actual rate limiting logic
        RateLimitCheckResponse response = new RateLimitCheckResponse();
        response.setAllowed(true);
        response.setMessage(Constants.MSG_REQUEST_ALLOWED);
        response.setRateLimitLimit((long) rateLimiterConfig.getDefaultLimits().getRequestsPerMinute());
        response.setRateLimitRemaining((long) rateLimiterConfig.getDefaultLimits().getRequestsPerMinute());
        response.setRateLimitReset(System.currentTimeMillis() + Constants.MILLIS_PER_MINUTE);

        if (rateLimiterConfig.getLogging().isLogAllRequests()) {
            logger.info("{} - msg=[Request allowed], identifier=[{}], type=[{}]",
                    Constants.LOG_PREFIX, request.getIdentifier(), request.getType());
        }

        return response;
    }

    /**
     * Get current rate limit configuration.
     */
    public RateLimitConfigDto getConfiguration() {
        logger.debug("{} - msg=[Getting configuration]", Constants.LOG_PREFIX);

        RateLimitConfigDto config = new RateLimitConfigDto();
        config.setAlgorithm(rateLimiterConfig.getAlgorithms().getDefaultAlgorithm());
        config.setRequestsPerMinute(rateLimiterConfig.getDefaultLimits().getRequestsPerMinute());
        config.setBurstSize(rateLimiterConfig.getDefaultLimits().getBurstSize());
        config.setEnabled(true); // Default to enabled

        return config;
    }

    /**
     * Update rate limit configuration.
     * This is a stub implementation for Milestone 1.
     */
    public void updateConfiguration(RateLimitConfigDto config) {
        logger.info("{} - msg=[Updating configuration], config=[{}]",
                Constants.LOG_PREFIX, config);

        // Validate configuration
        validateConfiguration(config);

        // In a real implementation, this would update the actual configuration
        // For now, we just log the update
        if (rateLimiterConfig.getLogging().isLogConfigurationChanges()) {
            logger.info("{} - msg=[Configuration updated], newConfig=[{}]",
                    Constants.LOG_PREFIX, config);
        }
    }

    /**
     * Extract real client IP from headers and remote address.
     */
    public String extractRealClientIp(String xForwardedFor, String xRealIp, String remoteAddr) {
        return validationUtils.extractRealClientIp(xForwardedFor, xRealIp, remoteAddr);
    }

    /**
     * Legacy method for backward compatibility.
     */
    public boolean isAllowed(String identifier) {
        logger.debug("{} - msg=[Legacy isAllowed check], identifier=[{}]",
                Constants.LOG_PREFIX, identifier);

        return true;
    }

    /**
     * Validate rate limit check request.
     */
    private void validateRequest(RateLimitCheckRequest request) {
        if (request.getIdentifier() == null || request.getIdentifier().trim().isEmpty()) {
            throw new IllegalArgumentException("Identifier is required");
        }

        if (request.getType() == null) {
            throw new IllegalArgumentException("Rate limit type is required");
        }

        // Validate identifier based on type
        switch (request.getType()) {
            case USER_ID:
                if (!validationUtils.isValidUserId(request.getIdentifier())) {
                    throw new IllegalArgumentException("Invalid user ID format");
                }
                break;
            case CLIENT_IP:
                if (!validationUtils.isValidIpAddress(request.getIdentifier())) {
                    throw new IllegalArgumentException("Invalid IP address format");
                }
                break;
            case API_KEY:
                if (!validationUtils.isValidApiKey(request.getIdentifier())) {
                    throw new IllegalArgumentException("Invalid API key format");
                }
                break;
            case ENDPOINT:
                if (!validationUtils.isValidEndpoint(request.getIdentifier())) {
                    throw new IllegalArgumentException("Invalid endpoint format");
                }
                break;
            case GLOBAL:
                // Global doesn't need specific validation
                break;
            default:
                throw new IllegalArgumentException("Unsupported rate limit type: " + request.getType());
        }
    }

    private void validateConfiguration(RateLimitConfigDto config) {
        if (config.getAlgorithm() == null) {
            throw new IllegalArgumentException("Algorithm is required");
        }

        if (config.getRequestsPerMinute() <= 0) {
            throw new IllegalArgumentException("Requests per minute must be positive");
        }

        if (config.getBurstSize() <= 0) {
            throw new IllegalArgumentException("Burst size must be positive");
        }
    }
}
