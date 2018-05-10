package com.Property.Dao;

import com.Property.Domain.Proprietor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProprietorDao
{
	List<Proprietor> getAll();

	Proprietor getSelfInfo(String id);

	int alterTel(@Param("tel")String tel, @Param("id")String id);
}
