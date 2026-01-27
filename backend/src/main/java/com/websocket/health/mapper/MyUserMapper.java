package com.websocket.health.mapper;

import com.websocket.health.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyUserMapper extends UserMapper{
    Integer MaxId();
    User queryUser(String account, String password);
}
