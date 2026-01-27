package com.websocket.health.mapper;

import com.websocket.health.pojo.Address;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 李佳伟
* @description 针对表【address】的数据库操作Mapper
* @createDate 2024-09-28 10:36:11
* @Entity com.websocket.health.pojo.Address
*/
@Mapper
public interface AddressMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);

}
