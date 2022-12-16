package com.tringuyen.food_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("")
    public ResponseEntity<?> saveDataToRedis() {
        redisTemplate.opsForValue().set("KeyA", "Helo Redis");
        String data = (String) redisTemplate.opsForValue().get("KeyA");
        System.out.println("Kiem tra redis " + data);

        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
