package com.service.authentication.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String PREFIX = "refresh_token:";

    public void save(UUID userId, String token, long ttlMs) {
        redisTemplate.opsForValue()
                .set(PREFIX + token, userId.toString(), ttlMs, TimeUnit.MILLISECONDS);
    }

    public UUID findUserIdByToken(String token) {
        String userId = (String) redisTemplate.opsForValue().get(PREFIX + token);
        return userId != null ? UUID.fromString(userId) : null;
    }

    public void delete(String token) {
        redisTemplate.delete(PREFIX + token);
    }

    public void deleteByUserId(UUID userId) {
        // Optional: implement if you keep list of tokens per user
    }
}
