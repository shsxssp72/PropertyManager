package com.Property.Mapper;

import com.Property.Entity.ChargingSituation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChargingSituationMapper
{
	List<ChargingSituation> getAll();
}
