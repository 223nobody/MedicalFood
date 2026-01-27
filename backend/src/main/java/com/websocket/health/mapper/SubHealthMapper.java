package com.websocket.health.mapper;

import com.websocket.health.pojo.SubHealth;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 李佳伟
* @description 针对表【sub_health】的数据库操作Mapper
* @createDate 2024-09-21 00:31:47
* @Entity com.websocket.health.pojo.SubHealth
*/
@Mapper
public interface SubHealthMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SubHealth record);

    int insertSelective(SubHealth record);

    SubHealth selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SubHealth record);

    int updateByPrimaryKey(SubHealth record);

}
