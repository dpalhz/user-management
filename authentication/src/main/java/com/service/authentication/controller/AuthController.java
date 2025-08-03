package com.service.authentication.controller;

import com.service.authentication.dto.TokenDto;
import com.service.authentication.dto.request.LoginRequest;
import com.service.authentication.dto.request.LogoutRequest;
import com.service.authentication.dto.request.RegisterRequest;
import com.service.authentication.dto.request.TokenRefreshRequest;
import com.service.authentication.dto.response.ApiResponse;
import com.service.authentication.dto.response.LoginResponse;
import com.service.authentication.service.AuthService;
import com.service.authentication.util.ApiResponseUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  /**
   * Registers a new user.
   *
   * @param request the request containing the user's email and password
   * @return a response with a message indicating that a verification email has been sent
   */
  @PostMapping("/register")
  public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody RegisterRequest request) {
    authService.register(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponseUtil.success(201, "Verification email sent.", null));
  }

  /**
   * Authenticates a user and returns an access and refresh token.
   *
   * @param request the request containing the user's email and password
   * @return a response with a LoginResponse containing the access and refresh tokens
   */
  @PostMapping("/login")
  public ResponseEntity<ApiResponse<LoginResponse>> login(
      @Valid @RequestBody LoginRequest request) {
    LoginResponse response = authService.login(request);
    return ResponseEntity.ok(ApiResponseUtil.success(200, "Login successful", response));
  }

  /**
   * Refreshes the access token using the provided refresh token.
   *
   * @param request the request containing the refresh token
   * @return a response with a new TokenResponse containing the refreshed access token
   */
  @PostMapping("/refresh-token")
  public ResponseEntity<ApiResponse<TokenDto>> refresh(
      @RequestBody TokenRefreshRequest request) {
    TokenDto tokenDto = authService.refreshToken(request);
    return ResponseEntity.ok(ApiResponseUtil.success(200, "Access token refreshed", tokenDto));
  }

  /**
   * Logs out the user by invalidating the provided refresh token.
   *
   * @param logoutRequest the request containing the refresh token to be invalidated
   * @return a response indicating that the logout was successful
   */
  @SecurityRequirement(name = "JWT Authentication")
  @PostMapping("/logout")
  public ResponseEntity<ApiResponse<Void>> logout(@RequestBody @Valid LogoutRequest logoutRequest) {
    authService.logout(logoutRequest.getRefreshToken());
    return ResponseEntity.ok(ApiResponseUtil.success(200, "Logout successful", null));
  }
}
