package com.Property.Mapper;

import com.Property.Entity.ChargingItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChargingItemMapper
{
	List<ChargingItem> getAll();
}
