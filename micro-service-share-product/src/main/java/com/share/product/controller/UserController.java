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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
//@DefaultProperties(defaultFallback = "defaultFallbackMethod")
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
    },fallbackMethod = "getFallbackMethod")
    public User get(@PathVariable("userId") Integer userId){
        if(userId == null || userId <= 0){
            throw new RuntimeException("非法的userId");
        }
        System.out.println("get...");
        return userService.get(userId);
    }

    public User getFallbackMethod(Integer userId){
        return new User();
    }

    @RequestMapping("/findAll")
    public List<User> findAll(String userIdList){
        System.out.println("findAll......");
        return userService.findAll(userIdList);
    }

    /**
     *
     * @param throwable 获取到的异常
     * @return
     */
    public User defaultFallbackMethod(Throwable throwable){
        return new User();
    }


    @RequestMapping("/user/{t1}/aaa")
    public String test(@PathVariable("t1") String t1, String t3, Integer userId, HttpServletRequest request){
        System.out.println("t1:"+t1);
        System.out.println("t3:"+t3);
        System.out.println("userId:"+userId);
        System.out.println(request.getParameter("userName"));
        return "success";
    }
}
