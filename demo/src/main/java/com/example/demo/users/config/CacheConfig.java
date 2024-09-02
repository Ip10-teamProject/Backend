package com.example.demo.users.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@Configuration
@EnableCaching
public class CacheConfig {

  // UserService에서 사용
  @Bean
  public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(connectionFactory);

    // String 타입의 key와 value를 사용하도록 Serializer 설정
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());

    return redisTemplate;
  }


  @Bean
  public RedisCacheManager cacheManager(
          RedisConnectionFactory redisConnectionFactory
  ) {
    // JSON 직렬화를 위한 설정
    RedisSerializer<Object> jsonSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
    RedisSerializer<String> stringSerializer = new StringRedisSerializer();

    RedisCacheConfiguration configuration = RedisCacheConfiguration
            .defaultCacheConfig()
            .disableCachingNullValues()
            .entryTtl(Duration.ofMinutes(60))
            .computePrefixWith(CacheKeyPrefix.simple())
            // 키는 문자열로, 값은 JSON으로 직렬화
            .serializeKeysWith(SerializationPair.fromSerializer(stringSerializer))
            .serializeValuesWith(SerializationPair.fromSerializer(jsonSerializer));

    return RedisCacheManager
            .builder(redisConnectionFactory)
            .cacheDefaults(configuration)
            .build();
  }
}

