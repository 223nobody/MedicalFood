package com.websocket.health.service;

import com.websocket.health.mapper.MySubHealthMapper;
import com.websocket.health.mapper.SubHealthMapper;
import com.websocket.health.pojo.SubHealth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubHealthService {
    @Autowired
    MySubHealthMapper mySubHealthMapper;
    public List<SubHealth> querySubHealth(String queryName)
    {
        if (queryName==null) queryName="";
        List<SubHealth> subHealths = mySubHealthMapper.querySubHealth(queryName);
        return subHealths;
    }

    public List<SubHealth> querySubHealthByFrequent()
    {
        List<SubHealth> subHealths = mySubHealthMapper.querySubHealthByFrequent();
        if(subHealths.size()>8) {
            List<SubHealth> subHealths1 = subHealths.subList(0, 8);
            return subHealths1;
        }
        return subHealths;
    }

    public List<SubHealth> querySubHealthByArticle(Integer articleId) {
        return mySubHealthMapper.querySubHealthByArticle(articleId);
    }
}
