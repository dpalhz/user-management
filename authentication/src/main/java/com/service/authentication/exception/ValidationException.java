package com.service.authentication.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends ApiException {
  public ValidationException(String message, String errorCode, HttpStatus status) {
    super(message, errorCode, status);
  }
}
