package com.Property.Dao;

import com.Property.Domain.Proprietor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface ProprietorDao
{
	List<Proprietor> getAll();

	Proprietor getSelfInfo(String id);

	int alterTel(@Param("tel")String tel, @Param("id")String id);

	/*根据参数值查询*/
	List<Proprietor> getProprietorbyParams(Map<String, Object> params);
}
