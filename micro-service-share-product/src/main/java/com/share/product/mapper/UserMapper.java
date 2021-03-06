package com.share.product.mapper;

import com.share.product.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {

    @Select("select * from user where user_id=#{userId}")
    User get(Integer userId);

    @Insert("insert into user(user_name,data_base) values(#{userName},DATABASE())")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    void save(User user);

    @Update("delete from user where user_id=#{userId}")
    void deleteUser(User user);

    @Select("<script>select * from user where user_id in " +
            "<foreach collection='list' open='(' item='userId' separator=',' close=')'> #{userId}</foreach>" +
            "</script>")
    List<User> findAll(List<Integer> userIdList);
}
