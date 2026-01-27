package com.websocket.health.service;

import com.websocket.health.mapper.MyAddressMapper;
import com.websocket.health.pojo.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    MyAddressMapper myAddressMapper;
    public List<Address> findAll(Integer userId)
    {
        return myAddressMapper.findAll(userId);
    }
    public Integer insertAddress(Address address)
    {
        Integer id = myAddressMapper.MaxId()+1;
        address.setId(id);
        myAddressMapper.insert(address);
        return id;
    }
    public Integer updateAddress(Address address)
    {
        return myAddressMapper.updateByPrimaryKeySelective(address);
    }
    public Integer deleteAddress(Integer id)
    {
        return myAddressMapper.deleteByPrimaryKey(id);
    }
}
