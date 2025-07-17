package com.service.authentication.dto.request; 
import lombok.Data;

@Data
public class TokenRefreshRequest {
    private String refreshToken;
}
