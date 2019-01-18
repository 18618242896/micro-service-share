package com.share.consume.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class consumeController {

    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping("/get/{userId}")
    public String get(@PathVariable("userId") Integer userId){
        System.out.println("");
        return restTemplate.getForObject("http://MICRO-SERVICE-SHARE-PRODUCT/get/{userId}",String.class,userId);
    }
}
