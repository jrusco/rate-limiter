# Product Requirements Document (PRD): Rate Limiting API

## 1. Overview
The Rate Limiting API is designed to protect backend services from abuse, ensure fair usage among users, and maintain system stability by restricting the number of requests a user or client can make within a specified time window. This document outlines the requirements, design considerations, and metrics for implementing a robust, secure, and scalable rate limiting solution.

## 2. Goals & Objectives
- Prevent abuse and denial-of-service (DoS) attacks by limiting excessive API usage.
- Ensure fair resource allocation among users/clients.
- Provide clear feedback to clients when limits are exceeded.
- Support both in-memory and distributed deployments for scalability.
- Enable monitoring and alerting for operational visibility.

## 3. Functional Requirements
### 3.1. Rate Limiting Algorithms
- **Support for multiple algorithms:**
  - Fixed Window Counter
  - Sliding Window Log
  - Token Bucket (preferred for flexibility)
- **Configurable limits:**
  - Allow per-user, per-IP, or per-API key configuration.
  - Support for global and endpoint-specific limits.
- **Burst Handling:**
  - Allow short bursts within the defined rate (token bucket).

### 3.2. Distributed Support
- **In-memory mode:** For single-node deployments (e.g., local dev, small scale).
- **Distributed mode:** Use Redis (or similar) for multi-node deployments to ensure consistent rate limiting across instances.

### 3.3. API Design
- **Endpoints:**
  - `POST /rate_limit/check` — Check if a request is allowed. Returns status and headers.
  - `GET /rate_limit/config` — Retrieve current rate limit configuration.
  - `POST /rate_limit/config` — Update rate limit configuration (admin only).
- **Response Headers:**
  - `X-RateLimit-Limit`: The request limit.
  - `X-RateLimit-Remaining`: Requests left in the current window.
  - `X-RateLimit-Reset`: Time when the window resets (UTC epoch seconds).
  - `Retry-After`: (If limited) Seconds until next allowed request.
- **Status Codes:**
  - `200 OK`: Request allowed.
  - `429 Too Many Requests`: Rate limit exceeded.

### 3.4. Security & Validation
- Validate all user input (user IDs, API keys, IPs) to prevent injection and abuse.
- Ensure rate limiting cannot be bypassed by spoofing headers.
- Log all limit breaches and suspicious activity.

### 3.5. Thread Safety & Concurrency
- Ensure thread-safe access to counters and state (locks or atomic operations).
- For distributed mode, use atomic Redis operations (e.g., INCR, EXPIRE, Lua scripts).

### 3.6. Metrics & Monitoring
- Track:
  - Allowed and blocked requests per user/client.
  - Rate limit breaches (with user/client info).
  - Latency of rate limit checks.
- Expose metrics via Prometheus or similar.
- Alert on high breach rates or system errors.

### 3.7. Logging
- Use structured logging:
  - Format: `RateLimiter - msg=[{log_message}], userId=[{user_id}], clientIp=[{client_ip}]`
  - Log all limit breaches, configuration changes, and errors.

### 3.8. Error Handling
- Return clear error messages and headers on limit breach.
- Handle backend (e.g., Redis) failures gracefully (fail open/closed as configured).

## 4. Non-Functional Requirements
- **Performance:** Rate limit checks must add minimal latency (<2ms in-memory, <10ms distributed).
- **Scalability:** Support 10,000+ requests/sec per node; scale horizontally.
- **Reliability:** No single point of failure in distributed mode.
- **Extensibility:** Easy to add new algorithms or limit types.
- **Security:** No sensitive data in logs; input validation everywhere.

## 5. Edge Cases & Considerations
- Handle clock skew in distributed deployments.
- Support for whitelisting trusted users/services.
- Graceful degradation if backend store is unavailable.
- Prevent resource leaks (e.g., memory, Redis keys).

## 6. Out of Scope
- Authentication/authorization (assume upstream service handles this).
- UI/dashboard for configuration (API only).

## 7. Open Questions
- Should rate limits be soft (warn before blocking) or hard (block immediately)?
- Should there be a self-service endpoint for users to check their own limits?

## 8. References
- [common algos and techniques to know](./source_material/common%20algos%20and%20techniques%20to%20know.md)
- [multithreading fundamentals](./source_material/multithreading%20fundamentals.md)
- [http](./source_material/http.md)
- [metrics - fundamental concepts](./source_material/metrics%20-%20fundamental%20concepts.md)
- [SE principles one liners](./source_material/SE%20principles%20one%20liners.md)
