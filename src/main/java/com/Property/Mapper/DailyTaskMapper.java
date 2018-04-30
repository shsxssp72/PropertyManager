package com.Property.Mapper;

import com.Property.Entity.DailyTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DailyTaskMapper
{
	List<DailyTask> getAll();
}
