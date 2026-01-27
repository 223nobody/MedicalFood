package com.websocket.health.service;

import com.websocket.health.mapper.MyAddressMapper;
import com.websocket.health.mapper.MyOrderMapper;
import com.websocket.health.mapper.MyUserMapper;
import com.websocket.health.pojo.Order;
import com.websocket.health.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    MyUserMapper myUserMapper;
    @Autowired
    MyAddressMapper myAddressMapper;
    @Autowired
    MyOrderMapper myOrderMapper;
    public User queryUser(String account, String password)
    {
        return myUserMapper.queryUser(account,password);
    }
    public Integer insertUser(User user)
    {
            Integer i = myUserMapper.MaxId()+1;
            user.setId(i);
            myUserMapper.insert(user);
            return i;
    }
    public Integer updateUser(User user)
    {
        return myUserMapper.updateByPrimaryKeySelective(user);
    }
    public Integer deleteUser(User user)
    {
        User user1 = myUserMapper.queryUser(user.getAccount(), user.getPassword());
        Integer id=user1.getId();
        if (id!=null) {
            myAddressMapper.deleteByUser(id);
            myOrderMapper.deleteByUser(id);
            return myUserMapper.deleteByPrimaryKey(id);
        }
        return null;
    }
}
