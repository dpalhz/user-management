package com.service.authentication.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthErrorCode {
  AUTH_INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Invalid username or password"),
  AUTH_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
  AUTH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Authentication token has expired"),
  AUTH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "Invalid authentication token"),
  AUTH_TOKEN_MISSING(HttpStatus.UNAUTHORIZED, "Authentication token is missing"),
  AUTH_ACCOUNT_LOCKED(HttpStatus.FORBIDDEN, "Your account is locked"),
  AUTH_ACCOUNT_DISABLED(HttpStatus.FORBIDDEN, "Your account is disabled"),
  AUTH_ACCESS_DENIED(HttpStatus.FORBIDDEN, "You do not have permission to access this resource"),
  AUTH_REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Refresh token has expired"),
  AUTH_REFRESH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "Invalid refresh token"),
  AUTH_SESSION_EXPIRED(HttpStatus.UNAUTHORIZED, "Your session has expired"),
  AUTH_EMAIL_NOT_VERIFIED(HttpStatus.FORBIDDEN, "Email has not been verified"),
  AUTH_2FA_REQUIRED(HttpStatus.UNAUTHORIZED, "Two-factor authentication is required"),
  AUTH_2FA_INVALID_CODE(HttpStatus.UNAUTHORIZED, "Invalid 2FA code"),
  AUTH_PROVIDER_MISMATCH(HttpStatus.BAD_REQUEST, "You signed up with a different provider"),
  AUTH_DUPLICATE_EMAIL(HttpStatus.CONFLICT, "Email is already registered"),
  AUTH_PASSWORD_TOO_WEAK(HttpStatus.BAD_REQUEST, "Password does not meet strength requirements"),
  AUTH_PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "Passwords do not match"),
  AUTH_USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "User with this email already exists"),
  AUTH_USER_NOT_ENABLED(HttpStatus.FORBIDDEN, "User account is not enabled");

  private final HttpStatus status;
  private final String message;

  AuthErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }
}
