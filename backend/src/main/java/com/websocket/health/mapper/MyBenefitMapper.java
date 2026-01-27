package com.websocket.health.mapper;

import com.websocket.health.pojo.Benefit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyBenefitMapper extends BenefitMapper{
    List<Benefit> findAll();
    List<Benefit> queryBenefitByFood(Integer foodId);
}
