package com.service.authentication.dto.response; 

import lombok.AllArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}
