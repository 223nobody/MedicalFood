package com.websocket.health.mapper;

import com.websocket.health.pojo.Benefit;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 李佳伟
* @description 针对表【benefit】的数据库操作Mapper
* @createDate 2024-09-25 12:32:36
* @Entity com.websocket.health.pojo.Benefit
*/
@Mapper
public interface BenefitMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Benefit record);

    int insertSelective(Benefit record);

    Benefit selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Benefit record);

    int updateByPrimaryKey(Benefit record);

}
