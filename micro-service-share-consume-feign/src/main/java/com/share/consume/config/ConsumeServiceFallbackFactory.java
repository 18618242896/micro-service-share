package com.share.consume.config;

import com.share.consume.entity.User;
import com.share.consume.service.ConsumeService;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Define a fallback factory for the specified Feign client interface. The fallback
 * factory must produce instances of fallback classes that implement the interface
 * annotated by {@link FeignClient}. The fallback factory must be a valid spring
 * bean.
 *  必须为spring的组件及实现FallbackFactory类
 * @see feign.hystrix.FallbackFactory for details.
 */
@Component
public class ConsumeServiceFallbackFactory implements FallbackFactory<ConsumeService> {
    @Override
    public ConsumeService create(Throwable cause) {
        System.out.println("message:"+cause.getMessage());
        cause.printStackTrace();
        return new ConsumeService() {
            @Override
            public User get(Integer userId) {
                return new User();
            }

            @Override
            public List<User> findAll(String userIdList) {
                return new ArrayList<>();
            }
        };
    }
}
