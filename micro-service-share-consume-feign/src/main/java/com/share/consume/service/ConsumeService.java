package com.share.consume.service;

import com.share.consume.config.ConsumeServiceFallbackFactory;
import config.ConsumeServiceFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
//path 是统一前缀
//@FeignClient(value = "MICRO-SERVICE-SHARE-PRODUCT",fallback = ConsumeServiceImpl.class,path = "/user")
@FeignClient(value = "MICRO-SERVICE-SHARE-PRODUCT",
        fallbackFactory = ConsumeServiceFallbackFactory.class,
        configuration = ConsumeServiceFeignConfig.class
)
public interface ConsumeService {


    @RequestMapping("/get/{userId}")
    String get(@PathVariable("userId") Integer userId);
}
