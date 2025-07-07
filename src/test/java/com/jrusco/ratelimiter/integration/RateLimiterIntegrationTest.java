package com.jrusco.ratelimiter.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrusco.ratelimiter.dto.RateLimitCheckRequest;
import com.jrusco.ratelimiter.dto.RateLimitConfigDto;
import com.jrusco.ratelimiter.enums.RateLimitAlgorithm;
import com.jrusco.ratelimiter.enums.RateLimitType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
class RateLimiterIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Test
    void testRateLimitCheckEndpoint() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        RateLimitCheckRequest request = new RateLimitCheckRequest();
        request.setIdentifier("user123");
        request.setType(RateLimitType.USER_ID);

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/rate_limit/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.allowed").value(true))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.rate_limit_limit").exists())
                .andExpect(jsonPath("$.rate_limit_remaining").exists())
                .andExpect(jsonPath("$.rate_limit_reset").exists());
    }

    @Test
    void testRateLimitCheckValidationError() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        RateLimitCheckRequest request = new RateLimitCheckRequest();
        // Missing required fields

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/rate_limit/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists());
    }

    @Test
    void testGetConfigurationEndpoint() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(get("/api/v1/rate_limit/config"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.algorithm").exists())
                .andExpect(jsonPath("$.requestsPerMinute").exists())
                .andExpect(jsonPath("$.burstSize").exists())
                .andExpect(jsonPath("$.enabled").exists());
    }

    @Test
    void testUpdateConfigurationEndpoint() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        RateLimitConfigDto config = new RateLimitConfigDto();
        config.setAlgorithm(RateLimitAlgorithm.TOKEN_BUCKET);
        config.setRequestsPerMinute(150);
        config.setBurstSize(15);
        config.setEnabled(true);

        String configJson = objectMapper.writeValueAsString(config);

        mockMvc.perform(post("/api/v1/rate_limit/config")
                .contentType(MediaType.APPLICATION_JSON)
                .content(configJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void testUpdateConfigurationValidationError() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        RateLimitConfigDto config = new RateLimitConfigDto();
        // Missing required fields and invalid values
        config.setRequestsPerMinute(-1);
        config.setBurstSize(0);

        String configJson = objectMapper.writeValueAsString(config);

        mockMvc.perform(post("/api/v1/rate_limit/config")
                .contentType(MediaType.APPLICATION_JSON)
                .content(configJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists());
    }

    @Test
    void testHealthEndpoint() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(get("/api/v1/rate_limit/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("rate-limiter"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
