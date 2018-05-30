package com.Property.Dao;

import com.Property.Domain.Building;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface BuildingDao
{
	List<Building> getAll();

	/*根据参数值查询*/
	List<Building> getBuildingbyParams(Map<String, Object> params);

	int addBuilding(Building building);

	int deleteBuilding(String id);

	int updateBuilding(Building building);
}
