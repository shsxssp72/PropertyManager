package com.Property.Dao;

import com.Property.Domain.Carpark;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface CarparkDao
{
	List<Carpark> getAll();

	Carpark getByID(String inputID);

	/*根据参数值查询*/
	List<Carpark> getCarparkbyParams(Map<String, Object> params);

	int addCarpark(Carpark carpark);

	int deleteCarpark(String id);

	int updateCarpark(Carpark carpark);

	//用于判定ID是否重复
	int getIdCount(String inputID);
}
