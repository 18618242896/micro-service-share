package com.share.product.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.share.product.entity.User;
import com.share.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@DefaultProperties(defaultFallback = "defaultFallbackMethod")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Environment environment;

    @Value("${apolloValue:defaultApolloValue}")
    private String apolloValue;

    /**
     * @see HystrixProperty
     * @see com.netflix.hystrix.HystrixCommandProperties
     * @param userId
     * @return
     */
    @RequestMapping("/get/{userId}")
    @HystrixCommand(commandKey = "user-commandKey",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10")
    })
    public User get(@PathVariable("userId") Integer userId){
        if(userId == null || userId <= 0){
            throw new RuntimeException("非法的userId");
        }
        if(userId %2 == 0){
            throw new RuntimeException("非法的userId-->偶数");
        }
        return userService.get(userId);
    }

    public String getFallbackMethod(Integer userId){
        System.out.println("fallback..............................");
        String profilesActive = environment.getProperty("spring.profiles.active");
        return "profilesActive:"+profilesActive+"\t:userId"+userId+"\tfallback...............";
    }

    /**
     *
     * @param throwable 获取到的异常
     * @return
     */
    public String defaultFallbackMethod(Throwable throwable){


        System.out.println("defaultFallbackMethod..............................");
        String profilesActive = environment.getProperty("spring.profiles.active");
        return "profilesActive:"+profilesActive+"\tdefaultFallbackMethod..............................";
    }
}
