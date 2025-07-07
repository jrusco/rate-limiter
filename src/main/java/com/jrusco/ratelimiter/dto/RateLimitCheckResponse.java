package com.jrusco.ratelimiter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jrusco.ratelimiter.util.ParsingUtils;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RateLimitCheckResponse {

    private boolean allowed;
    private String message;

    @JsonProperty("rate_limit_limit")
    private Long rateLimitLimit;

    @JsonProperty("rate_limit_remaining")
    private Long rateLimitRemaining;

    @JsonProperty("rate_limit_reset")
    private Long rateLimitReset;

    @JsonProperty("retry_after")
    private Long retryAfter;

    public RateLimitCheckResponse() {
    }

    public RateLimitCheckResponse(boolean allowed, String message) {
        this.allowed = allowed;
        this.message = message;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getRateLimitLimit() {
        return rateLimitLimit;
    }

    public void setRateLimitLimit(Long rateLimitLimit) {
        this.rateLimitLimit = rateLimitLimit;
    }

    public Long getRateLimitRemaining() {
        return rateLimitRemaining;
    }

    public void setRateLimitRemaining(Long rateLimitRemaining) {
        this.rateLimitRemaining = rateLimitRemaining;
    }

    public Long getRateLimitReset() {
        return rateLimitReset;
    }

    public void setRateLimitReset(Long rateLimitReset) {
        this.rateLimitReset = rateLimitReset;
    }

    public Long getRetryAfter() {
        return retryAfter;
    }

    public void setRetryAfter(Long retryAfter) {
        this.retryAfter = retryAfter;
    }

    @Override
    public String toString() {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"allowed\":").append(allowed);
        json.append(",\"message\":").append(ParsingUtils.escapeJsonString(message));
        json.append(",\"rate_limit_limit\":").append(rateLimitLimit);
        json.append(",\"rate_limit_remaining\":").append(rateLimitRemaining);
        json.append(",\"rate_limit_reset\":").append(rateLimitReset);
        json.append(",\"retry_after\":").append(retryAfter);
        json.append("}");
        return json.toString();
    }
}
