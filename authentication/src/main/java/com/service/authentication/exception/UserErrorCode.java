package com.service.authentication.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode {
  USER_PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "User profile not found"),
  USER_PROFILE_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update user profile"),
  USER_INVALID_REQUEST(HttpStatus.BAD_REQUEST, "Invalid request parameters");

  private final HttpStatus status;
  private final String message;

  UserErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }
}
