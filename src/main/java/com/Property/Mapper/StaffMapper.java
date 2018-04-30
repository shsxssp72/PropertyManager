package com.Property.Mapper;

import com.Property.Entity.Staff;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StaffMapper
{
	List<Staff> getAll();
}
