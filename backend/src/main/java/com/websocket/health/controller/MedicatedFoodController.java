package com.websocket.health.controller;

import com.websocket.health.pojo.MedicatedFood;
import com.websocket.health.pojo.Result;
import com.websocket.health.service.MedicatedFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin
@RestController
public class MedicatedFoodController {
    @Autowired
    MedicatedFoodService medicatedFoodService;
    @GetMapping("/medicatedFood")
    public Result queryMedicatedFood(String queryName)
    {
        List<MedicatedFood> medicatedFoods = medicatedFoodService.queryMedicatedFood(queryName);
        return Result.success(medicatedFoods);
    }
    @GetMapping("/medicatedFood/subHealth")
    public Result queryMedicatedFoodBySubHealth(Integer subHealthId)
    {
        return Result.success(medicatedFoodService.queryMedicatedFoodBySubHealth(subHealthId));
    }
    @GetMapping("/medicatedFood/id")
    public Result queryMedicatedFoodById(Integer id)
    {
        return Result.success(medicatedFoodService.queryMedicatedFoodById(id));
    }
    @GetMapping("/medicatedFood/benefit")
    public Result queryMedicatedFoodByBenefit(Integer benefitId)
    {
        return Result.success(medicatedFoodService.queryMedicatedFoodByBenefit(benefitId));
    }
    @GetMapping("/medicatedFood/order")
    public Result queryMedicatedFoodByOrder()
    {
        return Result.success(medicatedFoodService.queryMedicatedFoodByOrder());
    }
}
