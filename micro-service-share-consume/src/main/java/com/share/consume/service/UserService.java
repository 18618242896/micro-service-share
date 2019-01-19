package com.share.consume.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.share.consume.entity.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCollapser(batchMethod = "findAll",
//            collapserKey = "consumeServiceCommand",
            collapserProperties = {
                    @HystrixProperty(name = "timerDelayInMilliseconds",value = "2000"),
                    @HystrixProperty(name = "maxRequestsInBatch", value = "200"),
            },scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL    //REQUEST范围只对一个request请求内的多次服务请求进行合并，GLOBAL是多单个应用中的所有线程的请求中的多次服务请求进行合并。
    )
    public User get(Integer userId) {
        System.out.println("ribbon .................");
        return restTemplate.getForObject("http://MICRO-SERVICE-SHARE-PRODUCT/get/{userId}",User.class,userId);
    }

    @HystrixCommand(commandKey = "findAll")
    public List<User> findAll(List<Integer> userIdList){
        Map<String,Object> paramMap = Maps.newHashMap();
        System.out.println("ribbon findAll..........."+userIdList);
        paramMap.put("userIdList",StringUtils.join(userIdList,","));
        User[] userArray = restTemplate.getForObject("http://MICRO-SERVICE-SHARE-PRODUCT/findAll?userIdList={userIdList}", User[].class, paramMap);
        return Lists.newArrayList(userArray);
    }


}
