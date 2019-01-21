package com.stream.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.xml.transform.Source;

@EnableBinding(Source.class)
public class SinkSender {


    @SuppressWarnings("all")
    @Autowired
    @Qualifier("output")
    private MessageChannel output;


    public void send(String content){
        output.send(MessageBuilder.withPayload(content).build());
    }

}
