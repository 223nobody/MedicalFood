package com.websocket.health.controller;

import com.websocket.health.pojo.Benefit;
import com.websocket.health.pojo.Result;
import com.websocket.health.service.BenefitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin
@RestController
public class BenefitController {
    @Autowired
    BenefitService benefitService;
    @GetMapping("/benefit")
    public Result findAll()
    {
        return Result.success(benefitService.findAll());
    }
    @GetMapping("/benefit/medicatedFood")
    public Result queryBenefitByFood(Integer foodId)
    {
        return Result.success(benefitService.queryBenefitByFood(foodId));
    }
}
