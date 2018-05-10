package com.Property.Dao;

import com.Property.Domain.CarIORecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface CarIORecordDao
{
	List<CarIORecord> getAll();

	List<CarIORecord> getExternal();

	/*根据参数值查询*/
	List<CarIORecord> getCarIORecordbyParams(Map<String, Object> params);
}
