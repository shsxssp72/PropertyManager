package com.Property.Dao;

import com.Property.Domain.Facilities;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface FacilitiesDao
{
	List<Facilities> getAll();

	/*根据参数值查询*/
	List<Facilities> getFacilitiesbyParams(Map<String,Object> params);

	int addFacility(Facilities facilities);

	int deleteFacility(String id);

	int updateFacility(Facilities facilities);
}
