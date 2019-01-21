package com.stream.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(value = {OrderProcessor.class})
public class OrderBinding {

    private static Logger logger = LoggerFactory.getLogger(OrderBinding.class);

    @StreamListener(OrderProcessor.INPUT_ORDER)
    public void orderReceive(Message<String> message){
        System.out.println("======>receiveMessage");
        logger.info("headers:"+message.getHeaders());
        logger.info("payload:"+message.getPayload());
    }

    @SuppressWarnings("all")
    @Autowired
    @Qualifier("outputOrder")
    private MessageChannel outputOrder;

    public void send(String content){
        outputOrder.send(MessageBuilder.withPayload(content).build());
    }

}

