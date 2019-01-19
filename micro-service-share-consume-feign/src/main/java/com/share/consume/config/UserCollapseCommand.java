package com.share.consume.config;

import com.google.common.collect.Lists;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import com.share.consume.entity.User;
import com.share.consume.service.ConsumeService;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 请求合并器
 */
@Slf4j
public class UserCollapseCommand extends HystrixCollapser<List<User>,User,Integer> {

    private ConsumeService consumeService;
    private Integer userId;

    /**
     * 设置了请求时间窗为100ms，即请求时间间隔在100ms之内的请求会被合并为一个请求
     */
    public UserCollapseCommand(ConsumeService consumeService,Integer userId){
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("consumeServiceCommand"))
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(100)));
    }
    @Override
    public Integer getRequestArgument() {
        return userId;
    }

    /**
     * collapsedRequests保存了延迟时间窗中收集到所有获取单个user的请求
     * @param collapsedRequests
     * @return
     */
    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, Integer>> collapsedRequests) {
        List<Integer> userIdList = Lists.newArrayList();
        userIdList.addAll(collapsedRequests.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));

        return new UserBatchCommand(consumeService,userIdList);
    }

    @Override
    protected void mapResponseToRequests(List<User> batchResponse, Collection<CollapsedRequest<User, Integer>> collapsedRequests) {
        int count = 0;
        for(CollapsedRequest<User, Integer> collapsedRequest :collapsedRequests ){
            User user = batchResponse.get(count++);
            collapsedRequest.setResponse(user);
        }
    }
}
