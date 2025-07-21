package com.service.authentication.service.implementation;

import com.service.authentication.service.RefreshTokenRedisService;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenRedisServiceImpl implements RefreshTokenRedisService {

  private final StringRedisTemplate redisTemplate;
  private static final String REFRESH_TOKEN_PREFIX = "refreshToken:";

  public RefreshTokenRedisServiceImpl(StringRedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public void saveRefreshToken(String userId, String refreshToken, long expirationMs) {
    String key = REFRESH_TOKEN_PREFIX + refreshToken;
    redisTemplate.opsForValue().set(key, userId, expirationMs, TimeUnit.MILLISECONDS);
  }

  @Override
  public UUID getUserIdFromRefreshToken(String token) {
    String key = REFRESH_TOKEN_PREFIX + token;
    String userId = redisTemplate.opsForValue().get(key);
    return userId != null ? UUID.fromString(userId) : null;
  }

  @Override
  public boolean deleteRefreshToken(String token) {
    String key = REFRESH_TOKEN_PREFIX + token;
    return Boolean.TRUE.equals(redisTemplate.delete(key));
  }
}
