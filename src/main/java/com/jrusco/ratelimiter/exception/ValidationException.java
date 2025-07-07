package com.jrusco.ratelimiter.exception;

public class ValidationException extends RuntimeException {

    private final String field;
    private final String value;

    public ValidationException(String message) {
        super(message);
        this.field = null;
        this.value = null;
    }

    public ValidationException(String message, String field, String value) {
        super(message);
        this.field = field;
        this.value = value;
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
        this.field = null;
        this.value = null;
    }

    public ValidationException(String message, String field, String value, Throwable cause) {
        super(message, cause);
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public String getValue() {
        return value;
    }
}
