package com.jrusco.ratelimiter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jrusco.ratelimiter.enums.RateLimitAlgorithm;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RateLimitConfigDto {

    @NotNull(message = "Algorithm is required")
    private RateLimitAlgorithm algorithm;

    @Min(value = 1, message = "Requests per minute must be at least 1")
    private int requestsPerMinute;

    @Min(value = 1, message = "Burst size must be at least 1")
    private int burstSize;

    private boolean enabled;

    public RateLimitConfigDto() {
    }

    public RateLimitConfigDto(RateLimitAlgorithm algorithm, int requestsPerMinute, int burstSize, boolean enabled) {
        this.algorithm = algorithm;
        this.requestsPerMinute = requestsPerMinute;
        this.burstSize = burstSize;
        this.enabled = enabled;
    }

    public RateLimitAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(RateLimitAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"algorithm\":").append(algorithm != null ? "\"" + algorithm + "\"" : "null");
        json.append(",\"requestsPerMinute\":").append(requestsPerMinute);
        json.append(",\"burstSize\":").append(burstSize);
        json.append(",\"enabled\":").append(enabled);
        json.append("}");
        return json.toString();
    }
}
