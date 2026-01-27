package com.websocket.health.mapper;

import com.websocket.health.pojo.Order;
import com.websocket.health.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyOrderMapper extends OrderMapper{
    List<Order> findAll(Integer userId);
    Integer MaxId();
    void deleteByUser(Integer userId);
}
