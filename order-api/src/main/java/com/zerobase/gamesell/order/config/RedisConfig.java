package com.zerobase.gamesell.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    StringRedisSerializer serializer = new StringRedisSerializer();

    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(serializer);
    redisTemplate.setHashKeySerializer(serializer);
    Jackson2JsonRedisSerializer<Object> jsonRedisSerializer =
        new Jackson2JsonRedisSerializer<>(Object.class);
    redisTemplate.setValueSerializer(jsonRedisSerializer);
    redisTemplate.setHashValueSerializer(jsonRedisSerializer);
    return redisTemplate;

  }
}
