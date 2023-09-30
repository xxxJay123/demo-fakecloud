package com.example.demofakecloud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class RedisConfig {
  @Value("${spring.data.redis.host}")
  private String redisHost;

  @Value("${spring.data.redis.port}")
  private int redisPort;

  @Value("${spring.data.redis.database}")
  private int redisDatabase;

  @Value("${spring.data.redis.timeout}")
  private int redisTimeout;


  @Bean
  Jedis jedis() {
    Jedis jedis = new Jedis(redisHost, redisPort, redisTimeout);
    jedis.select(redisDatabase);
    return jedis;
  }
}
