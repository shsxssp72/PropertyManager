package com.Property.Dao;

import com.Property.Domain.Staff;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StaffDao
{
	List<Staff> getAll();

	Staff getSelfInfo(String id);

	List<Staff> getDeptStaff(String dept);
}
