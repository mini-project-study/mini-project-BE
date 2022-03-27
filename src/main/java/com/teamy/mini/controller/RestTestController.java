package com.teamy.mini.controller;

import com.teamy.mini.service.RedisTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RestTestController {

    @Autowired
    private RedisTestService redisTestService;

    @PostMapping("/getRedisStringValue")
    public void getRedisStringValue(String key) {
        log.info("Controller key : {}", key);
        redisTestService.getRedisStringValue(key);
    }

}
