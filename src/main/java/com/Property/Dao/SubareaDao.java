package com.Property.Dao;

import com.Property.Domain.Subarea;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubareaDao
{
	List<Subarea> getAll();
}
