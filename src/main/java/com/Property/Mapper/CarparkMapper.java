package com.Property.Mapper;

import com.Property.Entity.Carpark;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CarparkMapper
{
	List<Carpark> getAll();
}
