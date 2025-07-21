package com.service.authentication.dto;

import java.time.Instant;
import lombok.Data;

@Data
public class LoginDeviceDto {
  private String deviceName;
  private String ipAddress;
  private String location;
  private String userAgent;
  private Instant lastLoginAt;
}
