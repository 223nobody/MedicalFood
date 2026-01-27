package com.websocket.health.mapper;

import com.websocket.health.pojo.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyAddressMapper extends AddressMapper{
    List<Address> findAll(Integer userId);
    Integer MaxId();

    void deleteByUser(Integer userId);
}
