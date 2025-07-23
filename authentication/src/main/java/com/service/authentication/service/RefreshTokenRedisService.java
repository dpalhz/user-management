package com.service.authentication.service;

import java.util.UUID;

public interface RefreshTokenRedisService {
  void saveRefreshToken(String userId, String refreshToken, long expirationMs);

  UUID getUserIdFromRefreshToken(String token);

  boolean deleteRefreshToken(String token);
}
