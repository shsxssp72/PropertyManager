package com.Property.Mapper;

import com.Property.Entity.Building;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BuildingMapper
{
	List<Building> getAll();
}
