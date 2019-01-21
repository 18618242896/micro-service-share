package com.stream.controller;

import com.stream.config.OrderBinding;
import com.stream.config.SinkSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    private SinkSender sinkSender;

    @Autowired
    private OrderBinding orderBinding;

    @RequestMapping("/send")
    public String sendMessage(String content){
        sinkSender.send(content);
//        orderBinding.send(content);
        return "success";
    }
}
