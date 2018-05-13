package com.Property.Dao;

import com.Property.Domain.ChargingItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface ChargingItemDao
{
	List<ChargingItem> getAll();

	/*根据参数值查询*/
	List<ChargingItem> getChargingItembyParams(Map<String, Object> params);

	int addChargingItem(ChargingItem chargingItem);

	int deleteChargingItem(String id);

	int updateChargingItem(ChargingItem chargingItem);
}
