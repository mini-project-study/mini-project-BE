package com.teamy.mini.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisTestService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void getRedisStringValue(String key) {
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        log.info("Redis key : {}", key );
        log.info("Redis value : {}", stringValueOperations.get(key) );
    }
}
