package com.websocket.health.service;

import com.websocket.health.mapper.MyOrderMapper;
import com.websocket.health.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    MyOrderMapper myOrderMapper;
    public List<Order> findAll(Integer userId)
    {
        return myOrderMapper.findAll(userId);
    }
    public Integer addOrder(Order order)
    {
        Integer id = myOrderMapper.MaxId()+1;
        order.setId(id);
        order.setRq(new Date());
        myOrderMapper.insert(order);
        return id;
    }
    public Integer updateOrder(Order order)
    {
        return myOrderMapper.updateByPrimaryKeySelective(order);
    }
    public Integer deleteOrder(Integer id)
    {
        return myOrderMapper.deleteByPrimaryKey(id);
    }
}
