package com.Property.Mapper;

import com.Property.Entity.Proprietor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProprietorMapper
{
	List<Proprietor> getAll();
}
