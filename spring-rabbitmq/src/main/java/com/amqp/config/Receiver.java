package com.amqp.config;

import com.amqp.entity.SearchLog;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    //监听器监听指定的Queue
    @RabbitListener(queues = "hello")
    public void process(String msg){
        System.out.println("接收到消息,msg:"+msg);
    }

    @RabbitListener(queues = "searchLog")
    public void processLog(SearchLog searchLog){
        System.out.println(searchLog);
    }


    @RabbitListener(queues = "topic.message")
    public void process1(String content){
        System.out.println("process1============>"+content);
    }

    @RabbitListener(queues = "topic.queueMessages")
    public void process2(String content){
        System.out.println("process2============>"+content);
    }

    @RabbitListener(queues = "topic.hello")
    public void process3(String content){
        System.out.println("process3============>"+content);
    }

}
