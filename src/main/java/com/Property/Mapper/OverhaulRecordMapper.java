package com.Property.Mapper;

import com.Property.Entity.OverhaulRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OverhaulRecordMapper
{
	List<OverhaulRecord> getAll();
}
