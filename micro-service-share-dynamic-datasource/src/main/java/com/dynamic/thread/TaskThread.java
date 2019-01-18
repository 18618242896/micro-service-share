package com.dynamic.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskThread {

    private Logger logger = LoggerFactory.getLogger(TaskThread.class);

    @Scheduled(cron = "0/2 * * * * ? ")
    public void run(){
        logger.info("我是info级别日志");
        logger.error("我是error级别日志");
        logger.warn("我是warn级别日志");
        logger.debug("我是debug级别日志");
    }
}
