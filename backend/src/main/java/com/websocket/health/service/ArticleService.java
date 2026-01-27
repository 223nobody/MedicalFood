package com.websocket.health.service;

import com.websocket.health.mapper.MyArticleMapper;
import com.websocket.health.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private MyArticleMapper myArticleMapper;
    public List<Article> findAll()
    {
        return myArticleMapper.findAll();
    }
}
