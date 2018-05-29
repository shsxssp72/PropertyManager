package com.Property.Dao;

import com.Property.Domain.BuildingEntranceRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface BuildingEntranceRecordDao
{
	List<BuildingEntranceRecord> getAll();

	/*根据参数值查询*/
	List<BuildingEntranceRecord> getBERbyParams(Map<String,Object> params);

	int addBER(BuildingEntranceRecord buildingEntranceRecord);

	int deleteBER(String id);

	int updateBER(BuildingEntranceRecord buildingEntranceRecord);
}
