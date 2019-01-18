package com.dynamic.user.controller;

import com.dynamic.user.entity.User;
import com.dynamic.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {


    @Value("${apolloValue:123456}")
    private String apolloValue;

    @Autowired
    private UserService userService;

    @GetMapping("/get/{userId}")
    public User getUser(@PathVariable Integer userId){
        log.info("=============>接收到get请求=====getUserByUserId");
        if(userId == null || userId <= 0){
           throw new RuntimeException("非法的userId");
        }
        return userService.get(userId);
    }

    @PostMapping("/save")
    public User saveUser(String userName){
        log.info("=============>接收到post请求====>saveUser");
        if(userName == null ){
            throw new RuntimeException("未传递userName");
        }
        return userService.save(userName);
    }

    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable Integer userId){
        log.info("=============>接收到get请求====>deleteUser");
        if(userId == null || userId <= 0){
            throw new RuntimeException("非法的userId");
        }
        userService.deleteUser(userId);
        return "success";
    }


}
