package com.share.consume.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CommonConfig {

    @Bean
    @LoadBalanced   //负载均衡
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /**
     *
     * @return
     */
    @Bean
    public IRule iRule(){
        return new RandomRule();
    }
}
