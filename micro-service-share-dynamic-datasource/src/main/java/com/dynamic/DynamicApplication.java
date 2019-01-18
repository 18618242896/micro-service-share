package com.dynamic;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = {"com.dynamic.user.mapper"}) //扫描mapper
@EnableTransactionManagement                            //开启事物
@EnableApolloConfig                                     //开启阿波罗配置中心
@EnableCaching                                          //开启缓存
@EnableScheduling                                       //开启task
public class DynamicApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicApplication.class,args);
    }
}
