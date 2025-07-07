package com.jrusco.ratelimiter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jrusco.ratelimiter.enums.RateLimitType;
import com.jrusco.ratelimiter.util.ParsingUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RateLimitCheckRequest {

    @NotBlank(message = "Identifier is required")
    @Size(max = 255, message = "Identifier must not exceed 255 characters")
    private String identifier;

    @NotNull(message = "Rate limit type is required")
    private RateLimitType type;

    @Size(max = 255, message = "Endpoint must not exceed 255 characters")
    private String endpoint;

    @Size(max = 45, message = "Client IP must not exceed 45 characters")
    private String clientIp;

    @Size(max = 128, message = "API key must not exceed 128 characters")
    private String apiKey;

    public RateLimitCheckRequest() {
    }

    public RateLimitCheckRequest(String identifier, RateLimitType type) {
        this.identifier = identifier;
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public RateLimitType getType() {
        return type;
    }

    public void setType(RateLimitType type) {
        this.type = type;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String toString() {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"identifier\":").append(ParsingUtils.escapeJsonString(identifier));
        json.append(",\"type\":").append(type != null ? "\"" + type + "\"" : "null");
        json.append(",\"endpoint\":").append(ParsingUtils.escapeJsonString(endpoint));
        json.append(",\"clientIp\":").append(ParsingUtils.escapeJsonString(clientIp));
        json.append(",\"apiKey\":\"[MASKED]\"");
        json.append("}");
        return json.toString();
    }
}
