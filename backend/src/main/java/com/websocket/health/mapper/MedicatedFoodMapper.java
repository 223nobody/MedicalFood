package com.websocket.health.mapper;

import com.websocket.health.pojo.MedicatedFood;

/**
* @author 李佳伟
* @description 针对表【medicated_food】的数据库操作Mapper
* @createDate 2024-10-13 15:21:19
* @Entity com.websocket.health.pojo.MedicatedFood
*/
public interface MedicatedFoodMapper {

    int deleteByPrimaryKey(Long id);

    int insert(MedicatedFood record);

    int insertSelective(MedicatedFood record);

    MedicatedFood selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MedicatedFood record);

    int updateByPrimaryKey(MedicatedFood record);

}
