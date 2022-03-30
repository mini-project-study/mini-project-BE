package com.teamy.mini.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisTestService {

    private final StringRedisTemplate stringRedisTemplate;

    public void setLogoutToken(String key, long expire) {
        key = key.substring(7);
        stringRedisTemplate.opsForValue().set(key, "logout");
        stringRedisTemplate.expire(key, expire, TimeUnit.MILLISECONDS);
    }
    public String getRedisStringValue(String key) {
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        log.info("Redis key : {}", key );
        log.info("Redis value : {}", stringValueOperations.get(key) );
        return stringValueOperations.get(key);
    }


}
