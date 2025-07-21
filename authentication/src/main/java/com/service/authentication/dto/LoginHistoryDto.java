package com.service.authentication.dto;

import java.time.Instant;
import lombok.Data;

@Data
public class LoginHistoryDto {
  private Instant loginTime;
  private String ipAddress;
  private String location;
  private String deviceName;
  private boolean success;
}
