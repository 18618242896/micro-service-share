package com.share.consume.service;

import com.share.consume.config.ConsumeServiceFallbackFactory;
import com.share.consume.entity.User;
import config.ConsumeServiceFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//path 是统一前缀
//@FeignClient(value = "MICRO-SERVICE-SHARE-PRODUCT",fallback = ConsumeServiceImpl.class,path = "/user")
@FeignClient(value = "MICRO-SERVICE-SHARE-PRODUCT",
        fallbackFactory = ConsumeServiceFallbackFactory.class,
        configuration = ConsumeServiceFeignConfig.class
)
public interface ConsumeService {


    @RequestMapping("/get/{userId}")
    User get(@PathVariable("userId") Integer userId);

    @RequestMapping("/findAll")
    List<User> findAll(@RequestParam(value = "userIdList") String userIdList);
}
