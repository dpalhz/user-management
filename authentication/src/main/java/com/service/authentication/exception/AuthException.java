package com.service.authentication.exception;

public class AuthException extends ApiException {
  public AuthException(AuthErrorCode errorCode) {
    super(errorCode.getMessage(), errorCode.name(), errorCode.getStatus());
  }
}
