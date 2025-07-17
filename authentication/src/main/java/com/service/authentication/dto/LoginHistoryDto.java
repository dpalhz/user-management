package com.service.authentication.dto;

import lombok.Data;
import java.time.Instant;   

@Data
public class LoginHistoryDto {
    private Instant loginTime;
    private String ipAddress;
    private String location;
    private String deviceName;
    private boolean success;
    
}