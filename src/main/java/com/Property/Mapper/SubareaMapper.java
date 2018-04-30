package com.Property.Mapper;

import com.Property.Entity.Subarea;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubareaMapper
{
	List<Subarea> getAll();
}
