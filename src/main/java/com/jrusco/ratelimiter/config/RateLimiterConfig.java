package com.jrusco.ratelimiter.config;

import com.jrusco.ratelimiter.enums.RateLimitAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "rate-limiter")
@Validated
public class RateLimiterConfig {

    private DefaultLimits defaultLimits = new DefaultLimits();
    private Algorithms algorithms = new Algorithms();
    private Security security = new Security();
    private Logging logging = new Logging();

    public DefaultLimits getDefaultLimits() {
        return defaultLimits;
    }

    public void setDefaultLimits(DefaultLimits defaultLimits) {
        this.defaultLimits = defaultLimits;
    }

    public Algorithms getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(Algorithms algorithms) {
        this.algorithms = algorithms;
    }

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public Logging getLogging() {
        return logging;
    }

    public void setLogging(Logging logging) {
        this.logging = logging;
    }

    public static class DefaultLimits {
        @Min(value = 1, message = "Requests per minute must be at least 1")
        private int requestsPerMinute = 100;

        @Min(value = 1, message = "Burst size must be at least 1")
        private int burstSize = 10;

        public int getRequestsPerMinute() {
            return requestsPerMinute;
        }

        public void setRequestsPerMinute(int requestsPerMinute) {
            this.requestsPerMinute = requestsPerMinute;
        }

        public int getBurstSize() {
            return burstSize;
        }

        public void setBurstSize(int burstSize) {
            this.burstSize = burstSize;
        }
    }

    public static class Algorithms {
        @NotNull(message = "Default algorithm is required")
        private RateLimitAlgorithm defaultAlgorithm = RateLimitAlgorithm.TOKEN_BUCKET;

        public RateLimitAlgorithm getDefaultAlgorithm() {
            return defaultAlgorithm;
        }

        public void setDefaultAlgorithm(RateLimitAlgorithm defaultAlgorithm) {
            this.defaultAlgorithm = defaultAlgorithm;
        }
    }

    public static class Security {
        private List<String> trustedHeaders = List.of("X-Forwarded-For", "X-Real-IP");

        @Min(value = 1, message = "Max user ID length must be at least 1")
        private int maxUserIdLength = 255;

        @Min(value = 1, message = "Max API key length must be at least 1")
        private int maxApiKeyLength = 128;

        public List<String> getTrustedHeaders() {
            return trustedHeaders;
        }

        public void setTrustedHeaders(List<String> trustedHeaders) {
            this.trustedHeaders = trustedHeaders;
        }

        public int getMaxUserIdLength() {
            return maxUserIdLength;
        }

        public void setMaxUserIdLength(int maxUserIdLength) {
            this.maxUserIdLength = maxUserIdLength;
        }

        public int getMaxApiKeyLength() {
            return maxApiKeyLength;
        }

        public void setMaxApiKeyLength(int maxApiKeyLength) {
            this.maxApiKeyLength = maxApiKeyLength;
        }
    }

    public static class Logging {
        private boolean logAllRequests = false;
        private boolean logDeniedRequests = true;
        private boolean logConfigurationChanges = true;

        public boolean isLogAllRequests() {
            return logAllRequests;
        }

        public void setLogAllRequests(boolean logAllRequests) {
            this.logAllRequests = logAllRequests;
        }

        public boolean isLogDeniedRequests() {
            return logDeniedRequests;
        }

        public void setLogDeniedRequests(boolean logDeniedRequests) {
            this.logDeniedRequests = logDeniedRequests;
        }

        public boolean isLogConfigurationChanges() {
            return logConfigurationChanges;
        }

        public void setLogConfigurationChanges(boolean logConfigurationChanges) {
            this.logConfigurationChanges = logConfigurationChanges;
        }
    }
}
