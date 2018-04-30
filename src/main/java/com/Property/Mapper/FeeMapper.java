package com.Property.Mapper;

import com.Property.Entity.Fee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeeMapper
{
	List<Fee> getAll();
}
