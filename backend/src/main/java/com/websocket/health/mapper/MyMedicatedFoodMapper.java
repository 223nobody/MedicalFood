package com.websocket.health.mapper;

import com.websocket.health.pojo.MedicatedFood;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface MyMedicatedFoodMapper extends MedicatedFoodMapper{
    List<MedicatedFood> queryMedicatedFood(String queryName);
    List<MedicatedFood> queryMedicatedFoodBySubHealth(Integer subHealthId);
    List<MedicatedFood> queryMedicatedFoodByBenefit(Integer benefitId);
    List<MedicatedFood> queryMedicatedFoodByOrder();
}
