package com.websocket.health.controller;

import com.websocket.health.pojo.Article;
import com.websocket.health.pojo.Result;
import com.websocket.health.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @GetMapping("/article")
    public Result findAll()
    {
        List<Article> all = articleService.findAll();
        Result success = Result.success(all);
        success.setTotal(all.size());
        return success;
    }
}
