package com.service.authentication.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class LoginDeviceDto {
    private String deviceName;
    private String ipAddress;
    private String location;
    private String userAgent;
    private Instant lastLoginAt;
}