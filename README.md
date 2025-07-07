# Rate Limiter API

A robust, scalable rate limiting API built with Spring Boot that protects backend services from abuse, ensures fair usage among users, and maintains system stability.

## Features

- Multiple rate limiting algorithms (Token Bucket, Fixed Window, Sliding Window)
- Support for per-user, per-IP, and per-API key rate limiting
- In-memory and distributed (Redis) storage options
- Comprehensive input validation and security
- Structured logging with correlation IDs
- Health checks and metrics collection
- Configurable limits and algorithms
- RESTful API with proper HTTP status codes and headers

## Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Building the Application

```bash
mvn clean compile
```

### Running the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080/api/v1`

### Running Tests

```bash
mvn test
```

## API Endpoints

### Rate Limit Check

Check if a request is allowed based on rate limiting rules.

```http
POST /api/v1/rate_limit/check
Content-Type: application/json

{
  "identifier": "user123",
  "type": "USER_ID",
  "endpoint": "/api/v1/users",
  "clientIp": "192.168.1.1",
  "apiKey": "key123"
}
```

**Response:**
- `200 OK` - Request allowed
- `429 Too Many Requests` - Rate limit exceeded

### Get Configuration

Retrieve current rate limit configuration.

```http
GET /api/v1/rate_limit/config
```

### Update Configuration

Update rate limit configuration (admin only).

```http
POST /api/v1/rate_limit/config
Content-Type: application/json

{
  "algorithm": "TOKEN_BUCKET",
  "requestsPerMinute": 100,
  "burstSize": 10,
  "enabled": true
}
```

### Health Check

```http
GET /api/v1/rate_limit/health
```

## Configuration

The application uses Spring Boot's configuration system. Key configuration properties:

### Application Properties

```properties
# Rate Limiter Configuration
rate-limiter.default-limits.requests-per-minute=100
rate-limiter.default-limits.burst-size=10
rate-limiter.algorithms.default-algorithm=TOKEN_BUCKET
rate-limiter.security.trusted-headers[0]=X-Forwarded-For
rate-limiter.security.trusted-headers[1]=X-Real-IP
rate-limiter.security.max-user-id-length=255
rate-limiter.security.max-api-key-length=128
rate-limiter.logging.log-all-requests=false
rate-limiter.logging.log-denied-requests=true
rate-limiter.logging.log-configuration-changes=true
```

### Environment-Specific Configuration

For production deployment, override properties using environment variables or system properties:

```bash
# Production settings
java -jar rate-limiter.jar \
  -Dspring.profiles.active=prod \
  -Dlogging.level.com.jrusco.ratelimiter=WARN \
  -Dlogging.level.org.springframework.web=WARN \
  -Drate-limiter.default-limits.requests-per-minute=60 \
  -Drate-limiter.default-limits.burst-size=5 \
  -Drate-limiter.logging.log-all-requests=false
```

## Development

### Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/jrusco/ratelimiter/
│   │       ├── config/          # Configuration classes
│   │       ├── controller/      # REST controllers
│   │       ├── dto/             # Data transfer objects
│   │       ├── enums/           # Enumerations
│   │       ├── exception/       # Custom exceptions
│   │       ├── service/         # Business logic
│   │       └── util/            # Utility classes
│   └── resources/
│       ├── application.properties   # Application configuration
│       └── logback-spring.xml       # Logging configuration
└── test/
    └── java/
        └── com/jrusco/ratelimiter/
            ├── config/          # Configuration tests
            ├── integration/     # Integration tests
            └── util/            # Utility tests
```

### Code Style

- Use proper Java naming conventions
- Write comprehensive unit tests
- Follow Spring Boot best practices
- Include proper documentation and comments
- Use structured logging with meaningful messages

## Implementation Status

### ✅ Milestone 1: Project Setup & Core Infrastructure (COMPLETE)
- ✅ Project repository and directory structure
- ✅ Initial API skeleton with endpoints and request/response models
- ✅ Basic logging and configuration management
- ✅ Input validation utilities
- ✅ Unit tests for input validation and configuration loading
- ✅ Integration tests for API endpoints
- ✅ Exception handling with proper error responses
- ✅ Health check endpoint
- ✅ Structured logging with correlation IDs
- ✅ Configuration management with profiles

### 🔄 Milestone 2: In-Memory Rate Limiting (NEXT)
- Implement Token Bucket algorithm
- Add Fixed Window and Sliding Window stubs
- Thread-safe in-memory storage
- Actual rate limit enforcement
- Rate limit headers in responses

### 📋 Upcoming Milestones
- Milestone 3: Distributed Rate Limiting (Redis)
- Milestone 4: Configuration & Extensibility
- Milestone 5: Metrics, Monitoring & Error Handling
- Milestone 6: Security & Finalization

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass
6. Submit a pull request

## License

This project is licensed under the MIT License.
- **src/test/java/com/example/ratelimiter/RateLimiterApplicationTest.java**: Basic unit test

## Build & Run

```
mvn clean package
java -jar target/rate-limiter-0.0.1-SNAPSHOT.jar
```

## Test

```
mvn test
```
