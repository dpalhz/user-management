package com.service.authentication.exception;

public class UserException extends ApiException {
  public UserException(UserErrorCode errorCode) {
    super(errorCode.getMessage(), errorCode.name(), errorCode.getStatus());
  }
}
