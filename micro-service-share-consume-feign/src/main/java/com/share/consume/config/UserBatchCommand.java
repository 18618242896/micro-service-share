package com.share.consume.config;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.share.consume.entity.User;
import com.share.consume.service.ConsumeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import java.util.List;

/**
 * 对请求进行合并，实现一个批量请求命令
 */
@Slf4j
public class UserBatchCommand extends HystrixCommand<List<User>> {

    private ConsumeService consumeService;

    private List<Integer> userIdList;

    public UserBatchCommand(ConsumeService consumeService,List<Integer> userIdList){

        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("consumeServiceCommand")));
        this.consumeService = consumeService;
        this.userIdList = userIdList;
        log.info("批量请求命令.......");
       /* super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CollapsingGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("CollapsingKey")));
        */
    }

    @Override
    protected List<User> run() throws Exception {
        log.info("批量请求命令......."+userIdList);
        if(CollectionUtils.isEmpty(userIdList)){
            return null;
        }
        return consumeService.findAll(StringUtils.join(userIdList,","));
    }
}
