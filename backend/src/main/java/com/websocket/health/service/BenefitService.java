package com.websocket.health.service;

import com.websocket.health.mapper.MyBenefitMapper;
import com.websocket.health.pojo.Benefit;
import com.websocket.health.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BenefitService {
    @Autowired
    MyBenefitMapper myBenefitMapper;
    public List<Benefit> findAll()
    {
        return myBenefitMapper.findAll();
    }
    public List<Benefit> queryBenefitByFood(Integer foodId)
    {
        return myBenefitMapper.queryBenefitByFood(foodId);
    }
}
