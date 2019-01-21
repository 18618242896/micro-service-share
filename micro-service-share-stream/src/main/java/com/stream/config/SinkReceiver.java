package com.stream.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

/**
 * EnableBinding该注解用于指定一个或多个定义了@INPUT或@OUTPUT注解的接口
 */
@EnableBinding(value = {Sink.class, Source.class})
public class SinkReceiver {

    private static Logger logger = LoggerFactory.getLogger(SinkReceiver.class);

    @StreamListener(Sink.INPUT)
    public void receiveByte(byte[] payload){
        System.out.println("======>receiveByte");
        String s = new String(payload);
        logger.info("payload:"+s);
    }


    @StreamListener(Sink.INPUT)
    public void receiveMessage(Message<String> message){
        System.out.println("======>receiveMessage");
        MessageHeaders headers = message.getHeaders();
        String payload = message.getPayload();
        logger.info("headers:"+headers);
        logger.info("payload:"+payload);
    }
}
