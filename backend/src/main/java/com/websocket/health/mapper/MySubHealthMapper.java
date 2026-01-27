package com.websocket.health.mapper;

import com.websocket.health.pojo.SubHealth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MySubHealthMapper extends SubHealthMapper{
    List<SubHealth> querySubHealth(String queryName);
    List<SubHealth> querySubHealthByFrequent();
    List<SubHealth> querySubHealthByArticle(Integer articleId);
}
