package com.service.authentication.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtConfig {
  private String secret;
  private long accessTokenExpirationMs;
  private long refreshTokenExpirationMs;
}
