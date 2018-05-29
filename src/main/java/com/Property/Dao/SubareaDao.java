package com.Property.Dao;

import com.Property.Domain.Subarea;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface SubareaDao
{
	List<Subarea> getAll();

	/*根据参数值查询*/
	List<Subarea> getSubareabyParams(Map<String,Object> params);
}
