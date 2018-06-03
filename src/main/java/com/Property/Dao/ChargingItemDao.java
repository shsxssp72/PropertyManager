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

	ChargingItem getByID(String inputID);

	/*根据参数值查询*/
	List<ChargingItem> getChargingItembyParams(Map<String,Object> params);

	int addChargingItem(ChargingItem chargingItem);

	int deleteChargingItem(String id);

	int updateChargingItem(ChargingItem chargingItem);

	//用于判定ID是否重复
	int getIdCount(String inputID);
}
