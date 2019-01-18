package com.share.eureka.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;

@EnableEurekaServer                //开启eureka server注册服务仅对eureka注册中心有效
//@EnableDiscoveryClient            针对其他注册中心的
@SpringBootApplication
public class EurekaServerApplication {

    private static Logger LOGGER = LoggerFactory.getLogger(EurekaServerApplication.class);

    public static void main(String[] args) {
        LOGGER.info("eureka注册中心开始启动======》");
        ConfigurableApplicationContext applicationContext = SpringApplication.run(EurekaServerApplication.class, args);
        System.out.println("eureka注册中心启动完成======》");
        String profilesActive = applicationContext.getEnvironment().getProperty("spring.profiles.active");
        LOGGER.info("当前启动的为:"+profilesActive);
    }
}
