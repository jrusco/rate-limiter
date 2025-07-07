package com.jrusco.ratelimiter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.jrusco.ratelimiter.config.RateLimiterConfig;

@SpringBootApplication
@EnableConfigurationProperties(RateLimiterConfig.class)
public class RateLimiterApplication {
    public static void main(String[] args) {
        SpringApplication.run(RateLimiterApplication.class, args);
    }
}
