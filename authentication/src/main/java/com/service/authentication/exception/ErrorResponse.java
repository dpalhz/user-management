package com.service.authentication.exception;

import java.time.Instant;
import java.util.Map;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private int status;
    private String error;
    private String code;
    private String message;
    private Instant timestamp;
    private Map<String, Object> details;

    public ErrorResponse(ApiException ex) {
        this.status = ex.getStatus().value();
        this.error = ex.getStatus().getReasonPhrase();
        this.code = ex.getErrorCode();
        this.message = ex.getMessage();
        this.timestamp = Instant.now();
    }
    
    public ErrorResponse(ApiException ex, Map<String, Object> details) {
        this(ex);
        this.details = details;
    }
}
