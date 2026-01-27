package com.websocket.health.mapper;

import com.websocket.health.pojo.Article;

/**
* @author 李佳伟
* @description 针对表【article】的数据库操作Mapper
* @createDate 2024-10-16 15:23:38
* @Entity com.websocket.health.pojo.Article
*/
public interface ArticleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);

}
