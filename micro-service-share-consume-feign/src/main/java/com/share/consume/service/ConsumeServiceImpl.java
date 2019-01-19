package com.share.consume.service;

import com.share.consume.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Fallback class for the specified Feign client interface. The fallback class must
 * implement the interface annotated by this annotation and be a valid spring bean.
 */
@Component
public class ConsumeServiceImpl implements ConsumeService {
    @Override
    public User get(Integer userId) {
        User user = new User();
        user.setUserId(-1);
        user.setUserName("fail.....");
        return user;
    }

    @Override
    public List<User> findAll(String userIdList) {
        return null;
    }
}
