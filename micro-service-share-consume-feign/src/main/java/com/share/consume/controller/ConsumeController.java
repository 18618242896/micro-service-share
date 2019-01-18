package com.share.consume.controller;

import com.share.consume.service.ConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumeController {

    @Autowired
    private ConsumeService consumeService;

    @RequestMapping("/get/{userId}")
    public String get(@PathVariable("userId") Integer userId){
        System.out.println("");
        return consumeService.get(userId);
    }


}
