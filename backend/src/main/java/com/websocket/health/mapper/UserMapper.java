package com.websocket.health.mapper;

import com.websocket.health.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 李佳伟
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-09-28 10:35:28
* @Entity com.websocket.health.pojo.User
*/
@Mapper
public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

}
