package com.websocket.health.controller;

import com.websocket.health.pojo.Result;
import com.websocket.health.pojo.SubHealth;
import com.websocket.health.service.SubHealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin
@RestController
public class SubHealthController {
    @Autowired
    SubHealthService subHealthService;
    @GetMapping("/subHealth")
    public Result querySubHealth(String queryName)
    {
        List<SubHealth> subHealths = subHealthService.querySubHealth(queryName);
        return Result.success(subHealths);
    }

    @GetMapping("/subHealth/frequent")
    public Result  querySubHealthByFrequent()
    {
        List<SubHealth> subHealths = subHealthService.querySubHealthByFrequent();
        return Result.success(subHealths);
    }
    @GetMapping("/subHealth/article")
    public Result  querySubHealthByArticle(Integer articleId)
    {
        List<SubHealth> subHealths = subHealthService.querySubHealthByArticle(articleId);
        return Result.success(subHealths);
    }
}
