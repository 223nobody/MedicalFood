package com.websocket.health.mapper;

import com.websocket.health.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
/**
* @author 李佳伟
* @description 针对表【order】的数据库操作Mapper
* @createDate 2024-10-21 22:52:52
* @Entity com.websocket.health.pojo.Order
*/
public interface OrderMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

}
