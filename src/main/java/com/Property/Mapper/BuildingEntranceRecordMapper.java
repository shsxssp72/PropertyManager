package com.Property.Mapper;

import com.Property.Entity.BuildingEntranceRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BuildingEntranceRecordMapper
{
	List<BuildingEntranceRecord> getAll();
}
