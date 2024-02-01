package com.prueba.propiedades.general.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class DuplicatePropertyException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final LocalDateTime timestamp;

    public DuplicatePropertyException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.timestamp = LocalDateTime.now();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
