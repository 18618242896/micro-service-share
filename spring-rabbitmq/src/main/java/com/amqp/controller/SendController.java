package com.amqp.controller;

import com.amqp.entity.SearchLog;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class SendController {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @RequestMapping("/send")
    public void send() {

        SearchLog searchLog = new SearchLog();
        searchLog.setAccount("123");
        searchLog.setIpAddress("456");
        searchLog.setLogDate(new Date());
        searchLog.setSearchValue("789");
        rabbitTemplate.convertAndSend("searchLog", searchLog);
    }
}
