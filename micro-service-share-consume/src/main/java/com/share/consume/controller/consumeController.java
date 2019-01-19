package com.share.consume.controller;

import com.alibaba.fastjson.JSON;
import com.share.consume.entity.User;
import com.share.consume.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class consumeController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping("/get/{userId}")
    public String get(@PathVariable("userId") Integer userId){
        return JSON.toJSONString(userService.get(userId));
    }
}
