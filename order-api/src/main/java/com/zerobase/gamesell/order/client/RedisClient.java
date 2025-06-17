package com.zerobase.gamesell.order.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zerobase.gamesell.order.domain.redis.Cart;
import com.zerobase.gamesell.order.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import static com.zerobase.gamesell.order.exception.ErrorCode.CART_CHANGE_FAIL;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisClient {

  private final RedisTemplate<String, Object> redisTemplate;
  private static final ObjectMapper mapper;

  static {
    mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule()); // LocalDateTime 지원
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // ISO 8601 형식으로 저장
  }

  public <T> T get(Long key, Class<T> classType) {
    return get(key.toString(), classType);
  }

  private <T> T get(String key, Class<T> classType) {
    Object raw = redisTemplate.opsForValue().get(key);
    if (ObjectUtils.isEmpty(raw)) {
      return null;
    } else {
      try {
        return mapper.convertValue(raw, classType);
      } catch (IllegalArgumentException e) {
        log.error("Parsing error", e);
        return null;
      }
    }
  }


  public void put(Long key, Object value) {
    put(key.toString(), value);
  }

  public void put(String key, Object value) {
    try {
      redisTemplate.opsForValue().set(key, value);
    } catch (Exception e) {
      log.error("❌ Redis 저장 실패: {}", e.getMessage(), e);
      throw new RuntimeException("Redis 저장 실패", e);
    }
  }

  public void delete(Long key) {
    redisTemplate.delete(key.toString());
  }
}
