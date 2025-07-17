package com.service.authentication.controller;
import com.service.authentication.dto.response.LoginResponse;
import com.service.authentication.dto.response.TokenResponse;
import com.service.authentication.dto.request.LoginRequest;
import com.service.authentication.dto.request.RegisterRequest;
import com.service.authentication.dto.request.TokenRefreshRequest;
import com.service.authentication.service.AuthService;  
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("Verification email sent.");
    }

    /**
     * Authenticates a user and returns an access and refresh token.
     *
     * @param request the request containing the user's email and password
     * @return a response with a LoginResponse containing the access and refresh tokens
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * Refreshes the access token using the provided refresh token.
     *
     * @param request the request containing the refresh token
     * @return a response with a new TokenResponse containing the refreshed access token
     */

    /**
     * Refreshes the access token using the provided refresh token.
     *
     * @param request the request containing the refresh token
     * @return a response with a new TokenResponse containing the refreshed access token
     */
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody TokenRefreshRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    /**
     * Logs out the user by invalidating the refresh token associated with the provided access token.
     *
     * @param tokenHeader the authorization header containing the Bearer token
     * @return a response with no content indicating successful logout
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String tokenHeader) {
        authService.logout(tokenHeader);
        return ResponseEntity.noContent().build();
    }
}
