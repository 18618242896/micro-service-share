package com.dynamic.user.service;

import com.dynamic.user.entity.User;
import com.dynamic.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Slf4j
@CacheConfig(cacheNames = "user")
public class UserService {

    @SuppressWarnings("all")
    @Autowired
    private UserMapper userMapper;




    /**
     * 注意：@Cacheable不可配置condition = "#result != null,需要采用unless = "#result == null "来代替
     * @param userId    userId
     * @return          uer
     */
    @Cacheable(key = "'userId:'+#userId",unless = "#result == null ")
    public User get(Integer userId) {
        log.info("redis中不存在，db中去取,再放入内存中");
        return userMapper.get(userId);
    }


    @CachePut(key = "'userId:'+#result.userId",condition = "#result != null ")
    @Transactional
    public User save(String userName) {
        User user = new User();
        user.setUserName(userName);
        userMapper.save(user);
        return user;
    }


    @CacheEvict(key = "'userId:'+#userId",beforeInvocation = true)
    public void deleteUser(Integer userId){
        User user = new User();
        user.setUserId(userId);
        userMapper.deleteUser(user);
    }
}
