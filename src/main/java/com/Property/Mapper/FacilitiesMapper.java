package com.Property.Mapper;

import com.Property.Entity.Facilities;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FacilitiesMapper
{
	List<Facilities> getAll();
}
