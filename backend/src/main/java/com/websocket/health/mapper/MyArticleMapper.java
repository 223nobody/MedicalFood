package com.websocket.health.mapper;

import com.websocket.health.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyArticleMapper extends ArticleMapper{
    List<Article> findAll();
}
