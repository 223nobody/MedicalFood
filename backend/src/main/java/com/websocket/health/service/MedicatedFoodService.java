package com.websocket.health.service;

import com.websocket.health.mapper.MyMedicatedFoodMapper;
import com.websocket.health.pojo.MedicatedFood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicatedFoodService {
    @Autowired
    MyMedicatedFoodMapper myMedicatedFoodMapper;
    public List<MedicatedFood> queryMedicatedFood(String queryName)
    {
        if (queryName==null) queryName="";
        return myMedicatedFoodMapper.queryMedicatedFood(queryName);
    }
    public List<MedicatedFood> queryMedicatedFoodBySubHealth(Integer subHealthId)
    {
        return myMedicatedFoodMapper.queryMedicatedFoodBySubHealth(subHealthId);
    }
    public MedicatedFood queryMedicatedFoodById(Integer id)
    {
        return myMedicatedFoodMapper.selectByPrimaryKey(Long.valueOf(id));
    }
    public List<MedicatedFood> queryMedicatedFoodByBenefit(Integer benefitId)
    {
        return myMedicatedFoodMapper.queryMedicatedFoodByBenefit(benefitId);
    }
    public List<MedicatedFood> queryMedicatedFoodByOrder()
    {
        List<MedicatedFood> medicatedFoods = myMedicatedFoodMapper.queryMedicatedFoodByOrder();
        if (medicatedFoods.size()<=5) return medicatedFoods;
        else{
            List<MedicatedFood> top=new ArrayList<>();
            for (int i=0;i<5;i++)
            {
                top.add(medicatedFoods.get(i));
            }
            return top;
        }
    }
}
